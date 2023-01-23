package com.example.merakiapp.explicaciones

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
    private var pantallaSeleccionada = ""   // Pantalla enlazada al botón Siguiente

    // Valores de las pantallas a mostrar
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

        // Deshabilitar el Menu superior
        supportActionBar?.hide()
        var contadorPlayPause = 0

        // ---------------------- ANIMACIONES ----------------------

        // Por defecto: personajes invisibles
        binding.imgMirenBoca.visibility = ImageView.INVISIBLE
        binding.imgPatxiBoca.visibility = ImageView.INVISIBLE

        // Valores por defecto
        var animacionVisible = ""
        var estadoAnimacion = false

        // Controlar que personajes aparecen en cada Activity
        // PATXI
        if (pantallaSeleccionada == san_juan || pantallaSeleccionada == feria_pescado
            || pantallaSeleccionada == xixili || pantallaSeleccionada == gaztelugatxe) {
            binding.imgMiren.setVisibility(View.INVISIBLE)
            animacionVisible = "Patxi"
        }
        // MIREN
        if (pantallaSeleccionada == badatoz || pantallaSeleccionada == olatua
            || pantallaSeleccionada == izaro) {
            binding.imgPatxi.setVisibility(View.INVISIBLE)
            animacionVisible = "Miren"
        }

        // ------- ACTUALIZAR EL ESTADO DE LOS BOTONES "MEDIA PLAYER" AL ROTAR LA PANTALLA -------
        // STOP
        if (this.getSharedPreferences("pref", 0)?.getBoolean("Stop", false) == true) {
            stopService(intent)

            // ????
            val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()

            // Cambiar el icono del botón
            binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_play)

            // Resetear el contador
            contadorPlayPause = 0

            // Detener la animación
            estadoAnimacion = false
            animacionPatxiMiren(animacionVisible, estadoAnimacion)

        } else {
            // PLAY | PAUSE
            if ((this.getSharedPreferences("pref", 0).getInt("PlayPause", 1)) == 0) {
                // Cambiar el icono del botón
                binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_play)
                contadorPlayPause = 0

                // Detener la animación
                estadoAnimacion = false
                animacionPatxiMiren(animacionVisible, estadoAnimacion)

            } else {
                // Cambiar el icono del botón
                binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_pause)
                contadorPlayPause = 1

                // Activar la animación
                estadoAnimacion = true
                animacionPatxiMiren(animacionVisible, estadoAnimacion)
            }
        }

        // ------------------- CONTROL DE BOTONES ADICIONALES -------------------
        // AYUDA
        binding.btnAyudaExplicacion.setOnClickListener {
            val mensaje = mensajeExplicacion
            mostrar_dialog(this, tituloExplicacion, mensaje)
        }

        // INFO ROTACIÓN
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

        // El botón para Video SOLO es visible en "Feria del Pescado"
        if (pantallaSeleccionada == feria_pescado) {
            botonVideo?.visibility = View.VISIBLE
        } else {
            botonVideo?.visibility = View.INVISIBLE
        }

        botonVideo?.setOnClickListener {
            finish()
            var intent = Intent(this, ServicioAudios::class.java)
            stopService(intent)
            startActivity(Intent(this, VideoFeriaPescadoActivity::class.java))
        }

        // ---------------------- MEDIA PLAYER ----------------------

        // AUDIO - Valores por defecto
        var estadoAudio = ""
        var intent = Intent(this, ServicioAudios::class.java)

        // ---------------------- CONTROL DE BOTONES ----------------------
        // PLAY | PAUSE | RESUME
        binding.btnPlayPause.setOnClickListener {
            // Contador para saber cuantas veces se ha pulsado el botón
            // La primera vez, el valor será 1
            contadorPlayPause++

            // Comprobar si el número total de pulsaciones es par
            if (contadorPlayPause %2 == 0) {
                // ACCIÓN: PAUSE
                estadoAudio = "pause"

                // Contador reseteado
                contadorPlayPause = 0

                // Cambiar el icono del botón
                binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_play)

                // ???
                val PlayPause = this.getSharedPreferences("pref",0)
                    .edit().putInt("PlayPause",0).apply()
                val Stop = this.getSharedPreferences("pref",0).edit()
                    .putBoolean("Stop",false).apply()

                // Detener la animación
                estadoAnimacion = false
                animacionPatxiMiren(animacionVisible, estadoAnimacion)
            } else {
                // ACCIÓN: PLAY | RESUME
                estadoAudio = "play"

                // Cambiar el icono del botón
                binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_pause)

                // ???
                val PlayPause = this.getSharedPreferences("pref",0)
                    .edit().putInt("PlayPause",1).apply()
                val Stop = this.getSharedPreferences("pref",0)
                    .edit().putBoolean("Stop",false).apply()

                // Activar la animación
                estadoAnimacion = true
                animacionPatxiMiren(animacionVisible, estadoAnimacion)
            }

            // Iniciar el Servicio de Audio
            iniciarServicioAudio(estadoAudio, audioSeleccionado)
        }

        // STOP
        binding.btnStop.setOnClickListener {
            // ACCIÓN: STOP
            // Detener el Servicio
            stopService(intent)

            // ???
            val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",true).apply()

            // Cambiar el icono del botón
            binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_play)

            // Resetear el contador
            contadorPlayPause = 0

            // Detener la animación
            estadoAnimacion = false
            animacionPatxiMiren(animacionVisible, estadoAnimacion)
        }

        // RESTART
        binding.btnRestart.setOnClickListener {
            // ACCIÓN: RESTART
            estadoAudio = "restart"

            // La acción RESTART es similar al PLAY, por lo que el contador también aumenta
            contadorPlayPause = 1

            // Cambiar el icono del botón
            binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_pause)

            // Iniciar el Servicio de Audio
            iniciarServicioAudio(estadoAudio, audioSeleccionado)

            // Activar la animación
            estadoAnimacion = true
            animacionPatxiMiren(animacionVisible, estadoAnimacion)
        }

        // VOLVER
        binding.btnVolver.setOnClickListener {
            // ???
            val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()
            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

            // Detener el audio
            stopService(intent)

            // Volver a la Activity anterior
            finish()
            startActivity(Intent(this, MenuNav::class.java))
        }

        // SIGUIENTE
        binding.btnSiguiente.setOnClickListener {
            // ???
            val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()
            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

            // Detener el audio
            stopService(intent)

            // Controlar la siguiente Activity (Juego) que se abrirá
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

    // ---------------------- FUNCIONES ADICIONALES ----------------------

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

        // Animación en movimiento
        var animacion = AnimationUtils.loadAnimation(this, R.anim.animacion_patxi_miren)
        // Animación detenida
        var detenerAnimacion = AnimationUtils.loadAnimation(this, R.anim.detener_animacion)

        // Comprobar el estado de la animación recogida
        if (estadoAnimacion) {
            // ACTIVAR
            // Controlar que personajes son visibles
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
            // Controlar que personajes son visibles
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

    // Función que controla el botón Back del dispositivo móvil
    override fun onBackPressed() {
        // Detiene el audio que se está reproduciendo
        var intent = Intent(this, ServicioAudios::class.java)
        stopService(intent)

        // ???
        val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()
        val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()

        // Cierra la Activity actual
        finish()
    }

}