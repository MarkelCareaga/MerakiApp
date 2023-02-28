package com.example.merakiapp.juegos

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ActivityPuertaSanJuanBinding
import com.example.merakiapp.explicaciones.Explicaciones
import com.example.merakiapp.listas.ListaDialogos
import com.example.merakiapp.listas.ListaRecursos
import com.example.merakiapp.servicios.ServicioAudios

class PuertaSanJuanActivity : AppCompatActivity(), Explicaciones {
    private lateinit var binding: ActivityPuertaSanJuanBinding
    var estadoAudio = ""
    private var respuesta = 0

    private var listaDialogos = ListaDialogos()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityPuertaSanJuanBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // --------------------- BOTONES AYUDA Y ROTACIÓN ---------------------
        // AYUDA
        binding.btnAyudaPuertaSanJuan.setOnClickListener {
            val mensaje = ListaRecursos.mensajePuertaSanJuan
            listaDialogos.mostrar_dialog(this, ListaRecursos.tituloJuegos, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaPuertaSanJuan.setOnClickListener {
            listaDialogos.mostrar_info_pantalla(this, false)
        }


        // ----------------------AUDIO AL INICIAR EL JUEGO--------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Juego_PuertaSanJuan)

        // Conexión con el Servicio de Audios
        var intent = Intent(this, ServicioAudios::class.java)


        // -------------------------------------------------------------------------
        // FONDO
        val activityPuertaSanJuan = binding.activityPuertaSanJuan
        activityPuertaSanJuan.background =
            resources.getDrawable(ListaRecursos.fondo_PuertaSanJuan, theme)

        // Ocultar el GIF de los aplausos
        binding.gifAplausosPuertaSanJuan.visibility = ImageView.INVISIBLE

        // Ocultar el botón de Finalizar
        binding.btnSiguientePuertaSanJuan.visibility = Button.GONE


        // ------------------------ CONTROL DE BOTONES ------------------------
        // COMPROBAR RESULTADO
        binding.btnComprobarPuertaSanJuan.setOnClickListener {
            comprobarRespuestas()
        }

        // VOLVER
        binding.btnVolverPuertaSanJuan.setOnClickListener {
            stopService(intent)
            finish()

            intent = abrirExplicacion(this, ListaRecursos.pantalla_PuertaSanJuan)
            startActivity(intent)
        }

        // FINALIZAR
        binding.btnSiguientePuertaSanJuan.setOnClickListener {
            stopService(intent)
            finish()
            startActivity(Intent(this, SopaLetrasActivity::class.java))
        }

    }


    // ---------------------- FUNCIONES ADICIONALES ----------------------
    // Función para comprobar el resultado de las respuestas
    private fun comprobarRespuestas() {
        // Recoger respuesta introducida
        val respuesta_texto = binding.txtRespuestaPuertaSanJuan.text.toString()

        if (respuesta_texto.isEmpty()) {
            respuesta = 0
        } else {
            respuesta = respuesta_texto.toInt()
        }

        // Comprobar si todas las respuestas son correctas
        if (respuesta == 7) {
            // Elementos a ocultar
            binding.btnComprobarPuertaSanJuan.visibility = Button.GONE
            binding.btnVolverPuertaSanJuan.visibility = Button.GONE

            // Elementos a mostrar
            binding.gifAplausosPuertaSanJuan.visibility = ImageView.VISIBLE
            binding.btnSiguientePuertaSanJuan.visibility = Button.VISIBLE

            // Mostrar el GIF de los aplausos
            mostrarGif()

            // Reproducir audio
            estadoAudio = "play"
            iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Gritos)

            // Cambiar color
            binding.txtRespuestaPuertaSanJuan.setBackgroundColor(Color.GREEN)

        } else {
            // Cambiar color
            binding.txtRespuestaPuertaSanJuan.setBackgroundColor(Color.RED)
            binding.txtRespuestaPuertaSanJuan.setText("")
        }
    }

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

    // Función para mostrar el GIF de los aplausos
    private fun mostrarGif() {
        val imageView: ImageView = binding.gifAplausosPuertaSanJuan
        Glide.with(this).load(R.drawable.aplausos).into(imageView)
    }

    // Función que controla el botón Back del dispositivo móvil
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Detiene el audio que se está reproduciendo
        var intent = Intent(this, ServicioAudios::class.java)
        stopService(intent)

        // Abre la activity de Explicación
        finish()
        intent = abrirExplicacion(this, ListaRecursos.pantalla_PuertaSanJuan)
        startActivity(intent)
    }
}