package com.example.merakiapp.explicaciones

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.merakiapp.*
import com.example.merakiapp.databinding.ActivityExplicacionesBinding
import com.example.merakiapp.juegos.*
import com.example.merakiapp.listas.ListaDialogos
import com.example.merakiapp.listas.ListaRecursos
import com.example.merakiapp.sqLite.SeleccionarUsuario
import com.example.merakiapp.servicios.ServicioAudios

// ACTIVITY DE DEMOSTRACIÓN: APARTADO PARA EXPLICAR LA UBICACIÓN SELECCIONADA

class ExplicacionesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExplicacionesBinding

    private var listaDialogos = ListaDialogos()

    private var audioSeleccionado = 0       // Audio a reproducir
    private var fondoSeleccionado = 0       // Fondo a mostrar
    private var textoSeleccionado = ""      // Texto a mostrar
    private var pantallaSeleccionada = ""   // Pantalla enlazada al botón Siguiente
    private var contadorPlayPause: Int = 0
    private var estadoAnimacion: Boolean = false
    private var estadoAudio: String = ""

    // Valores de las pantallas a mostrar
    companion object {
        const val intro = "introduccion"
        const val san_juan = "puerta_de_san_juan"
        const val badatoz = "badatoz_estatua"
        const val feria_pescado = "feria_del_pescado"
        const val olatua = "olatua_estatua"
        const val xixili = "xixili"
        const val izaro = "isla_de_izaro"
        const val gaztelugatxe = "gaztelugatxe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExplicacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deshabilitar el Menu superior
        supportActionBar?.hide()

        // ---------------------- ANIMACIONES ----------------------

        // Por defecto: personajes invisibles
        binding.imgMirenBoca.visibility = ImageView.INVISIBLE
        binding.imgPatxiBoca.visibility = ImageView.INVISIBLE

        // Valores por defecto
        var animacionVisible = ""

        // Controlar que personajes aparecen en cada Activity
        // PATXI
        if (pantallaSeleccionada == san_juan || pantallaSeleccionada == feria_pescado
            || pantallaSeleccionada == xixili || pantallaSeleccionada == gaztelugatxe
        ) {
            binding.imgMiren.visibility = View.INVISIBLE
            animacionVisible = "Patxi"
        }
        // MIREN
        if (pantallaSeleccionada == badatoz || pantallaSeleccionada == olatua
            || pantallaSeleccionada == izaro
        ) {
            binding.imgPatxi.visibility = View.INVISIBLE
            animacionVisible = "Miren"
        }

        // ------- ACTUALIZAR EL ESTADO DE LOS BOTONES "MEDIA PLAYER" AL ROTAR LA PANTALLA -------
        // STOP
        if (this.getSharedPreferences("pref", 0)?.getBoolean("Stop", false) == true) {
            stopService(intent)

            this.getSharedPreferences("pref", 0).edit().putBoolean("Stop", false).apply()

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
            val mensaje = ListaRecursos.mensajeExplicacion
            listaDialogos.mostrar_dialog(this, ListaRecursos.tituloExplicacion, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaExplicacion.setOnClickListener {
            listaDialogos.mostrar_info_pantalla(this, true)
        }


        // Añadir funcionalidad de Scroll al "Texto explicativo"
        binding.txtExplicacionDemo.movementMethod = ScrollingMovementMethod()

        // Recuperar datos enviados desde la anterior Activity
        audioSeleccionado = intent.getIntExtra("audioSeleccionado", 0)
        fondoSeleccionado = intent.getIntExtra("fondoSeleccionado", 0)
        textoSeleccionado = intent.getStringExtra("textoSeleccionado").toString()
        pantallaSeleccionada = intent.getStringExtra("pantallaSeleccionada").toString()

        // Establecer el fondo del Activity
        binding.fondoDemo.setBackgroundResource(fondoSeleccionado)

        // Establecer el texto explicativo del Activity
        binding.txtExplicacionDemo.text = textoSeleccionado


        // --------------- ACTIVAR / DESACTIVAR BOTÓN DEL VIDEO ---------------
        val botonVideo = binding.btnAccederVideo

        // El botón para Video SOLO es visible en "Feria del Pescado"
        if (pantallaSeleccionada == feria_pescado) {
            botonVideo.visibility = View.VISIBLE
        } else {
            botonVideo.visibility = View.INVISIBLE
        }

        botonVideo.setOnClickListener {
            finish()
            val intent = Intent(this, ServicioAudios::class.java)
            stopService(intent)
            startActivity(Intent(this, VideoFeriaPescadoActivity::class.java))
        }

        // ---------------------- MEDIA PLAYER ----------------------

        // AUDIO - Valores por defecto
        val intent = Intent(this, ServicioAudios::class.java)

        // ---------------------- CONTROL DE BOTONES ----------------------
        // PLAY | PAUSE | RESUME
        binding.btnPlayPause.setOnClickListener {
            // Contador para saber cuantas veces se ha pulsado el botón
            // La primera vez, el valor será 1
            contadorPlayPause++

            // Comprobar si el número total de pulsaciones es par
            if (contadorPlayPause % 2 == 0) {
                // ACCIÓN: PAUSE
                estadoAudio = "pause"

                // Contador reseteado
                contadorPlayPause = 0

                // Cambiar el icono del botón
                binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_play)

                this.getSharedPreferences("pref", 0).edit().putInt("PlayPause", 0).apply()
                this.getSharedPreferences("pref", 0).edit().putBoolean("Stop", false).apply()

                // Detener la animación
                estadoAnimacion = false
                animacionPatxiMiren(animacionVisible, estadoAnimacion)
            } else {
                // ACCIÓN: PLAY | RESUME
                estadoAudio = "play"

                // Cambiar el icono del botón
                binding.btnPlayPause.setImageResource(R.drawable.ic_baseline_audio_pause)

                this.getSharedPreferences("pref", 0).edit().putInt("PlayPause", 1).apply()
                this.getSharedPreferences("pref", 0).edit().putBoolean("Stop", false).apply()

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

            this.getSharedPreferences("pref", 0).edit().putBoolean("Stop", true).apply()

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
            this.getSharedPreferences("pref", 0).edit().putBoolean("Stop", false).apply()
            this.getSharedPreferences("pref", 0).edit().putInt("PlayPause", 0).apply()

            // Detener el audio
            stopService(intent)

            // Volver a la Activity anterior
            finish()
            startActivity(Intent(this, MenuNav::class.java))
        }

        // SIGUIENTE
        binding.btnSiguiente.setOnClickListener {
            this.getSharedPreferences("pref", 0).edit().putBoolean("Stop", false).apply()
            this.getSharedPreferences("pref", 0).edit().putInt("PlayPause", 0).apply()

            // Detener el audio
            stopService(intent)

            // Controlar la siguiente Activity (Juego) que se abrirá
            when (pantallaSeleccionada) {
                intro -> {
                    finish()
                    startActivity(Intent(this, MenuNav::class.java).putExtra("explicacion", true))
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
        val intent = Intent(this, ServicioAudios::class.java)
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
        val animacion = AnimationUtils.loadAnimation(this, R.anim.animacion_patxi_miren)
        // Animación detenida
        val detenerAnimacion = AnimationUtils.loadAnimation(this, R.anim.detener_animacion)

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
                }
                else -> {
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
                }
                else -> {
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
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Detiene el audio que se está reproduciendo
        val intent = Intent(this, ServicioAudios::class.java)
        stopService(intent)

        this.getSharedPreferences("pref", 0).edit().putInt("PlayPause", 0).apply()
        this.getSharedPreferences("pref", 0).edit().putBoolean("Stop", false).apply()

        // Volver a la Activity anterior
        finish()
        startActivity(Intent(this, MenuNav::class.java))

    }


}