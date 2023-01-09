package com.example.merakiapp

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.merakiapp.databinding.ActivityFinalBinding
import com.example.merakiapp.servicios.ServicioAudios

class FinalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinalBinding

    // AUDIO Y FONDO
    private var audioSeleccionado = R.raw.felicidades            // Audio a reproducir
    private var fondoSeleccionado = R.drawable.fondo_final       // Fondo a mostrar
    var estadoAudio = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        super.onCreate(savedInstanceState)
        binding=ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CAMBIAR COLOR DEL TEXTO
        binding.txtFelicidades.setTextColor(Color.WHITE)

        // -------------------------------- DIALOGS --------------------------------
        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaFinal.setOnClickListener {
            val mensaje = mensajeFinal
            mostrar_dialog(this, tituloFinal, mensaje)
        }
        binding.btnInfoPantallaFinal.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }
        // -------------------------------------------------------------------------

        // ----------------------AUDIO AL INICIAR LA ACTIVITY--------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, audioSeleccionado)
        // ----------------------------------------------------------------------------

        // Conexión con el Servicio de Audios
        var intent = Intent(this, ServicioAudios::class.java)

        // FONDO
        var activityFinal = binding.activityFinal
        activityFinal.background = resources.getDrawable(fondoSeleccionado, theme)

        // Mostrar el GIF de los aplausos
        mostrarGif()

        // CONTROL DE BOTONES
        binding.btnSiguienteFinal.setOnClickListener {
            stopService(intent)
            finish()
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

    // Función para mostrar el GIF de los aplausos
    private fun mostrarGif() {
        val ImageView: ImageView = binding.gifAplausosFinal
        Glide.with(this).load(R.drawable.aplausos).into(ImageView)
    }
}