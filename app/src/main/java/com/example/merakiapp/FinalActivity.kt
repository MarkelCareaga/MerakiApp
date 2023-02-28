package com.example.merakiapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.merakiapp.databinding.ActivityFinalBinding
import com.example.merakiapp.listas.ListaDialogos
import com.example.merakiapp.listas.ListaRecursos
import com.example.merakiapp.servicios.ServicioAudios
import java.util.*
import kotlin.concurrent.schedule

class FinalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinalBinding

    // AUDIO Y FONDO
    var estadoAudio = ""

    private var listaDialogos = ListaDialogos()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CAMBIAR COLOR DEL TEXTO
        binding.txtFelicidades.setTextColor(Color.WHITE)


        // ------------------------ BOTONES AYUDA Y ROTACIÓN ------------------------
        // AYUDA
        binding.btnAyudaFinal.setOnClickListener {
            val mensaje = ListaRecursos.mensajeFinal
            listaDialogos.mostrar_dialog(this, ListaRecursos.tituloFinal, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaFinal.setOnClickListener {
            listaDialogos.mostrar_info_pantalla(this, false)
        }


        // ----------------------AUDIO AL INICIAR LA ACTIVITY--------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Miren)

        // Conexión con el Servicio de Audios
        val intent = Intent(this, ServicioAudios::class.java)

        // Después de 6 segundos, se reproduce el segundo audio
        Timer().schedule(6000) {
            iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Patxi)
        }


        // ---------------------------------------------------------------------
        // FONDO
        val activityFinal = binding.activityFinal
        activityFinal.background = resources.getDrawable(ListaRecursos.fondo_Introduccion, theme)

        // Mostrar el GIF de los aplausos
        mostrarGif()


        // ------------------------ CONTROL DE BOTONES ------------------------
        // SIGUIENTE
        binding.btnSiguienteFinal.setOnClickListener {
            stopService(intent)
            finish()
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

    // Función para mostrar el GIF de los aplausos
    private fun mostrarGif() {
        val imageView: ImageView = binding.gifAplausosFinal
        Glide.with(this).load(R.drawable.aplausos).into(imageView)
    }

    // Función que controla el botón Back del dispositivo móvil
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this, ServicioAudios::class.java)
        stopService(intent)
        finish()
    }
}