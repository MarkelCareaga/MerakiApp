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

class VideoFeriaPescadoActivity : AppCompatActivity(), Dialogos, Explicaciones {
    private lateinit var binding: ActivityVideoFeriaPescadoBinding
    private var fondoSeleccionado = R.drawable.fondoferiapescado
    var recursoFeriaPescado = Recurso(this, "feria_del_pescado", R.raw.audioferiadelpescado, R.drawable.fondoferiapescado)

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Deshabilitar menu superior
        supportActionBar?.hide()

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

            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()
            val Stop = this.getSharedPreferences("pref",0).edit().putBoolean("Stop",false).apply()

            var intent = abrirExplicacion(recursoFeriaPescado)
            startActivity(intent)
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