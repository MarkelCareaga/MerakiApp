package com.example.merakiapp.juegos

import android.R
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.merakiapp.*
import com.example.merakiapp.databinding.ActivityVideoFeriaPescadoBinding

class VideoFeriaPescadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoFeriaPescadoBinding
    private var fondoSeleccionado = com.example.merakiapp.R.drawable.fondoferiapescado

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        super.onCreate(savedInstanceState)
        binding = ActivityVideoFeriaPescadoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // -------------------------------- DIALOGS --------------------------------
        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaVideoFeriaPescado.setOnClickListener {
            val mensaje = mensajeVideoFeriaPescado
            mostrar_dialog(this, tituloVideo, mensaje)
        }
        binding.btnInfoPantallaVideoFeriaPescado.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }
        // -------------------------------------------------------------------------

        // FONDO
        var activityVideoFeriaPescado = binding.activityVideoFeriaPescado
        activityVideoFeriaPescado.background = resources.getDrawable(fondoSeleccionado, theme)

        // VIDEO PLAYER
        reproducirVideo("videoarrainazoka")

        // CONTROL DE BOTONES
        binding.btnVolverDesdeVideo.setOnClickListener {
            finish()
        }
    }

    private fun reproducirVideo(videoSeleccionado: String) {
        val rawId = resources.getIdentifier(
            videoSeleccionado, "raw",
            packageName
        )
        val path = "android.resource://$packageName/$rawId"

        val videoView = binding.videoFeriaPescado
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(Uri.parse(path))
        videoView.requestFocus()
        videoView.start()
    }
}