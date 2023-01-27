package com.example.merakiapp.juegos

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.merakiapp.databinding.ActivityVideoFeriaPescadoBinding
import com.example.merakiapp.explicaciones.Explicaciones
import com.example.merakiapp.listas.ListaDialogos
import com.example.merakiapp.listas.ListaRecursos

class VideoFeriaPescadoActivity : AppCompatActivity(), Explicaciones {
    private lateinit var binding: ActivityVideoFeriaPescadoBinding

    private var listaDialogos = ListaDialogos()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityVideoFeriaPescadoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // -------------------------- BOTONES AYUDA Y ROTACIÓN --------------------------
        // AYUDA
        binding.btnAyudaVideoFeriaPescado.setOnClickListener {
            val mensaje = ListaRecursos.mensajeVideoFeriaPescado
            listaDialogos.mostrar_dialog(this, ListaRecursos.tituloVideo, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaVideoFeriaPescado.setOnClickListener {
            listaDialogos.mostrar_info_pantalla(this, false)
        }


        // -------------------------------------------------------------------------
        // FONDO
        val activityVideoFeriaPescado = binding.activityVideoFeriaPescado
        activityVideoFeriaPescado.background = resources.getDrawable(ListaRecursos.fondo_FeriaPescado, theme)

        // VIDEO PLAYER
        reproducirVideo()

        // -------------------------- CONTROL DE BOTONES --------------------------
        // VOLVER
        binding.btnVolverDesdeVideo.setOnClickListener {
            finish()

            this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()
            this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()

            val intent_feria_pescado = abrirExplicacion(this, ListaRecursos.pantalla_FeriaPescado)
            startActivity(intent_feria_pescado)
        }
    }

    // Función para reproducir video
    private fun reproducirVideo() {
        val videoSeleccionado = "videoarrainazoka"
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