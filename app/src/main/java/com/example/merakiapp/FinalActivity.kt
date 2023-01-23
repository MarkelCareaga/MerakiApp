package com.example.merakiapp

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.merakiapp.Dialogos.Companion.mensajeFinal
import com.example.merakiapp.Dialogos.Companion.tituloFinal
import com.example.merakiapp.databinding.ActivityFinalBinding
import com.example.merakiapp.servicios.ServicioAudios
import java.util.*
import kotlin.concurrent.schedule


class FinalActivity : AppCompatActivity(), Dialogos, Recursos {
    private lateinit var binding: ActivityFinalBinding

    // AUDIO Y FONDO
    var estadoAudio = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        super.onCreate(savedInstanceState)
        binding=ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CAMBIAR COLOR DEL TEXTO
        binding.txtFelicidades.setTextColor(Color.WHITE)


        // ------------------------ BOTONES AYUDA Y ROTACIÓN ------------------------
        // AYUDA
        binding.btnAyudaFinal.setOnClickListener {
            val mensaje = mensajeFinal
            mostrar_dialog(this, tituloFinal, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaFinal.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }


        // ----------------------AUDIO AL INICIAR LA ACTIVITY--------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, Recursos.audio_Miren)

        // Conexión con el Servicio de Audios
        var intent = Intent(this, ServicioAudios::class.java)

        // Después de 6 segundos, se reproduce el segundo audio
        Timer().schedule(6000) {
            iniciarServicioAudio(estadoAudio, Recursos.audio_Patxi)
        }


        // ---------------------------------------------------------------------
        // FONDO
        var activityFinal = binding.activityFinal
        activityFinal.background = resources.getDrawable(Recursos.fondo_Introduccion, theme)

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
        var intent = Intent(this, ServicioAudios::class.java)

        // Pasar el estado del audio a reproducir
        intent.putExtra("estadoAudio", estadoAudio)
        // Pasar el audio a reproducir
        intent.putExtra("audioSeleccionado", audioSeleccionado)

        // Iniciar el Servicio
        startService(intent)
    }

    // Función para mostrar el GIF de los aplausos
    private fun mostrarGif() {
        val ImageView: ImageView = binding.gifAplausosFinal
        Glide.with(this).load(R.drawable.aplausos).into(ImageView)
    }

    // Función que controla el botón Back del dispositivo móvil
    override fun onBackPressed() {
        var intent = Intent(this, ServicioAudios::class.java)
        stopService(intent)
        finish()
    }
}