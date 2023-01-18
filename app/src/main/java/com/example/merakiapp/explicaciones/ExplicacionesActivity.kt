package com.example.merakiapp.explicaciones

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.merakiapp.*
import com.example.merakiapp.Dialogos.Companion.mensajeExplicacion
import com.example.merakiapp.Dialogos.Companion.tituloExplicacion
import com.example.merakiapp.databinding.ActivityExplicacionesBinding
import com.example.merakiapp.juegos.*
import com.example.merakiapp.sqLite.SeleccionarUsuario
import com.example.merakiapp.servicios.ServicioAudios

// ACTIVITY DE DEMOSTRACIÓN: APARTADO PARA EXPLICAR LA UBICACIÓN SELECCIONADA

class ExplicacionesActivity : AppCompatActivity(), Dialogos {
    private lateinit var binding: ActivityExplicacionesBinding

    private var audioSeleccionado = 0       // Audio a reproducir
    private var fondoSeleccionado = 0       // Fondo a mostrar
    private var textoSeleccionado = ""      // Texto a mostrar
    private var pantallaSeleccionada = ""   // Pantalla enlazada al boton Siguiente


    //var intent = Intent(this, ServicioAudios::class.java)

    companion object {
        private val intro = "introduccion"
        private val san_juan = "puerta_de_san_juan"
        private val badatoz = "badatoz_estatua"
        private val feria_pescado = "feria_del_pescado"
        private val olatua = "olatua_estatua"
        private val xixili = "xixili"
        private val izaro = "isla_de_izaro"
        private val gaztelugatxe = "gaztelugatxe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExplicacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Deshabilitar Menu superior
        supportActionBar?.hide()

        // Por defecto
        binding.imgMirenBoca.visibility = ImageView.INVISIBLE
        binding.imgPatxiBoca.visibility = ImageView.INVISIBLE
        var contadorPlayPause = 0


        // ANIMACIONES
        var animacionVisible = ""
        var estadoAnimacion = false
        // PATXI | MIREN
        if (pantallaSeleccionada == san_juan || pantallaSeleccionada == feria_pescado
            || pantallaSeleccionada == xixili || pantallaSeleccionada == gaztelugatxe) {
            binding.imgMiren.setVisibility(View.INVISIBLE)
            animacionVisible = "Patxi"
        }

        if (pantallaSeleccionada == badatoz || pantallaSeleccionada == olatua
            || pantallaSeleccionada == izaro) {
            binding.imgPatxi.setVisibility(View.INVISIBLE)
            animacionVisible = "Miren"
        }


       if (this.getSharedPreferences("pref", 0)?.getBoolean("Stop", false) == true) {
           stopService(intent)
           val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()

           // Cambiar icono del botón
           binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_play)

           contadorPlayPause = 0

           // Detener animación
           estadoAnimacion = false
           animacionPatxiMiren(animacionVisible, estadoAnimacion)

       } else {
           if ((this.getSharedPreferences("pref", 0).getInt("PlayPause", 1)) == 0) {
               binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_play)
               contadorPlayPause = 0
               estadoAnimacion = false
               animacionPatxiMiren(animacionVisible, estadoAnimacion)

           } else {
               binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_pause)
               contadorPlayPause = 1
               estadoAnimacion = true
               animacionPatxiMiren(animacionVisible, estadoAnimacion)
           }

       }
        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaExplicacion.setOnClickListener {
            val mensaje = mensajeExplicacion
            mostrar_dialog(this, tituloExplicacion, mensaje)
        }
        binding.btnInfoPantallaExplicacion.setOnClickListener {
            mostrar_info_pantalla(this, true)
        }

        // Añadir funcionalidad de Scroll al "Texto explicativo"
        binding.txtExplicacionDemo.setMovementMethod(ScrollingMovementMethod())

        // Recuperar datos enviados desde la anterior Activity
        audioSeleccionado = intent.getIntExtra("audioSeleccionado", 0)
        fondoSeleccionado = intent.getIntExtra("fondoSeleccionado", 0)
        textoSeleccionado = intent.getStringExtra("textoSeleccionado").toString()
        pantallaSeleccionada = intent.getStringExtra("pantallaSeleccionada").toString()

        // Establecer el fondo del Activity
        binding.fondoDemo.setBackgroundResource(fondoSeleccionado)

        // Establecer el texto explicativo del Activity
        binding.txtExplicacionDemo.setText(textoSeleccionado)

        // --------------- ACTIVAR / DESACTIVAR BOTÓN DEL VIDEO ---------------
        var botonVideo = binding.btnAccederVideo

        if (pantallaSeleccionada == feria_pescado) {
            botonVideo?.visibility = View.VISIBLE
        } else {
            botonVideo?.visibility = View.INVISIBLE
        }

        botonVideo?.setOnClickListener {
            var intent = Intent(this, ServicioAudios::class.java)
            stopService(intent)
            startActivity(Intent(this, VideoFeriaPescadoActivity::class.java))
        }
        //---------------------------------------------------------------------


        // AUDIO - Valores por defecto
        var estadoAudio = ""
        var intent = Intent(this, ServicioAudios::class.java)


        // CONTROL DE BOTONES
        // PLAY | PAUSE | RESUME
        binding.btnPlayPause.setOnClickListener {
            contadorPlayPause++
            if (contadorPlayPause %2 == 0) {
                // PAUSE
                estadoAudio = "pause"
                contadorPlayPause = 0
                // Cambiar icono del botón
                binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_play)
                val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()
                val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()

                // Detener animación
                estadoAnimacion = false
                animacionPatxiMiren(animacionVisible, estadoAnimacion)
            } else {
                // PLAY | RESUME
                estadoAudio = "play"

                // Cambiar icono del botón
                binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_pause)

                val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",1).apply()
                val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()

                // Activar animación
                estadoAnimacion = true
                animacionPatxiMiren(animacionVisible, estadoAnimacion)
            }

            // Iniciar el Servicio
            iniciarServicioAudio(estadoAudio, audioSeleccionado)
        }

        // STOP
        binding.btnStop.setOnClickListener {
            // Detener Servicio
            stopService(intent)
            val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",true).apply()

            // Cambiar icono del botón
            binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_play)

            contadorPlayPause = 0

            // Detener animación
            estadoAnimacion = false
            animacionPatxiMiren(animacionVisible, estadoAnimacion)
        }

        // RESTART
        binding.btnRestart.setOnClickListener {
            estadoAudio = "restart"
            contadorPlayPause = 1

            // Cambiar icono del botón
            binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_pause)

            // Iniciar el Servicio
            iniciarServicioAudio(estadoAudio, audioSeleccionado)

            // Activar animación
            estadoAnimacion = true
            animacionPatxiMiren(animacionVisible, estadoAnimacion)
        }

        // VOLVER
        binding.btnVolver.setOnClickListener {
            val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()
            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

            // Detener el audio
            stopService(intent)
            // Volver al anterior Activity
            finish()
        }

        // SIGUIENTE
        binding.btnSiguiente.setOnClickListener {
            val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()
            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

            stopService(intent)

            when (pantallaSeleccionada) {
                intro -> {
                    finish()
                    startActivity(Intent(this, MenuNav::class.java).putExtra("explicacion",true))
                }
                san_juan -> {
                    finish()
                    startActivity(Intent(this, PuertaSanJuanActivity::class.java))
                }
                badatoz -> {
                    finish()
                    startActivity(Intent(this, BadatozEstatuaActivity::class.java))
                }
                feria_pescado -> {
                    finish()
                    startActivity(Intent(this, FeriaPescadoActivity::class.java))
                }
                olatua -> {
                    finish()
                    startActivity(Intent(this, OlatuaEstatuaActivity::class.java))
                }
                xixili -> {
                    finish()
                    startActivity(Intent(this, XixiliActivity::class.java))
                }
                izaro -> {
                    finish()
                    startActivity(Intent(this, SeleccionarUsuario::class.java))
                }
                gaztelugatxe -> {
                    finish()
                    startActivity(Intent(this, GaztelugatxeActivity::class.java))
                }
            }
        }
    }


    // Función para gestionar los audios (Media Player)
    private fun iniciarServicioAudio(estadoAudio: String, audioSeleccionado: Int) {

        // Indicar el Servico a iniciar
        var intent = Intent(this, ServicioAudios::class.java)

        // Pasar el estado del audio a reproducir
        intent.putExtra("estadoAudio", estadoAudio)
        // Pasar el audio a reproducir
        intent.putExtra("audioSeleccionado", audioSeleccionado)

        // Iniciar el Servicio
        startService(intent)
    }


    // Función para ejecutar o detener las animaciones
    private fun animacionPatxiMiren(animacionVisible: String, estadoAnimacion: Boolean) {

        var animacion = AnimationUtils.loadAnimation(this, R.anim.animacion_patxi_miren)
        var detenerAnimacion = AnimationUtils.loadAnimation(this, R.anim.detener_animacion)

        if (estadoAnimacion) {
            // ACTIVAR
            // Controlar que animación se debe ejecutar
            when (animacionVisible) {
                "Patxi" -> {
                    binding.imgPatxi.visibility = ImageView.INVISIBLE
                    binding.imgPatxiBoca.visibility = ImageView.VISIBLE
                    binding.imgPatxiBoca.startAnimation(animacion)
                }
                "Miren" -> {
                    binding.imgMiren.visibility = ImageView.INVISIBLE
                    binding.imgMirenBoca.visibility = ImageView.VISIBLE
                    binding.imgMirenBoca.startAnimation(animacion)
                } else -> {
                    binding.imgPatxi.visibility = ImageView.INVISIBLE
                    binding.imgPatxiBoca.visibility = ImageView.VISIBLE
                    binding.imgPatxiBoca.startAnimation(animacion)

                    binding.imgMiren.visibility = ImageView.INVISIBLE
                    binding.imgMirenBoca.visibility = ImageView.VISIBLE
                    binding.imgMirenBoca.startAnimation(animacion)
                }
            }
        } else {
            // DETENER
            // Controlar que animación se debe ejecutar
            when (animacionVisible) {
                "Patxi" -> {
                    binding.imgPatxiBoca.startAnimation(detenerAnimacion)
                    binding.imgPatxiBoca.visibility = ImageView.INVISIBLE
                    binding.imgPatxi.visibility = ImageView.VISIBLE
                }
                "Miren" -> {
                    binding.imgMirenBoca.startAnimation(detenerAnimacion)
                    binding.imgMirenBoca.visibility = ImageView.INVISIBLE
                    binding.imgMiren.visibility = ImageView.VISIBLE
                } else -> {
                    binding.imgPatxiBoca.startAnimation(detenerAnimacion)
                    binding.imgPatxiBoca.visibility = ImageView.INVISIBLE
                    binding.imgPatxi.visibility = ImageView.VISIBLE

                    binding.imgMirenBoca.startAnimation(detenerAnimacion)
                    binding.imgMirenBoca.visibility = ImageView.INVISIBLE
                    binding.imgMiren.visibility = ImageView.VISIBLE
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        stopService(intent)
        val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()
    }

    override fun onResume() {
        super.onResume()


    }
}