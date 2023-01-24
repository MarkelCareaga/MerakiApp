package com.example.merakiapp.juegos

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.merakiapp.*
import com.example.merakiapp.Dialogos.Companion.mensajeVideoFeriaPescado
import com.example.merakiapp.Dialogos.Companion.tituloVideo
import com.example.merakiapp.databinding.ActivityVideoFeriaPescadoBinding
import com.example.merakiapp.Explicaciones

class VideoFeriaPescadoActivity : AppCompatActivity(), Dialogos, Explicaciones {
    private lateinit var binding: ActivityVideoFeriaPescadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        // Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityVideoFeriaPescadoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // -------------------------- BOTONES AYUDA Y ROTACIÓN --------------------------
        // AYUDA
        binding.btnAyudaVideoFeriaPescado.setOnClickListener {
            val mensaje = mensajeVideoFeriaPescado
            mostrar_dialog(this, tituloVideo, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaVideoFeriaPescado.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }


        // -------------------------------------------------------------------------
        // FONDO
        var activityVideoFeriaPescado = binding.activityVideoFeriaPescado
        activityVideoFeriaPescado.background = resources.getDrawable(Recursos.fondo_FeriaPescado, theme)

        // VIDEO PLAYER
        reproducirVideo("videoarrainazoka")

        // -------------------------- CONTROL DE BOTONES --------------------------
        // VOLVER
        binding.btnVolverDesdeVideo.setOnClickListener {
            finish()

            // ???
            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()
            val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()

            var intent_feria_pescado = abrirExplicacion(this, Recursos.pantalla_FeriaPescado,
                Recursos.audio_FeriaPescado, Recursos.fondo_FeriaPescado)
            startActivity(intent_feria_pescado)
        }
    }

    // Función para reproducir video
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