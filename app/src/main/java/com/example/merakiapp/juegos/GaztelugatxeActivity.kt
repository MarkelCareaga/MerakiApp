package com.example.merakiapp.juegos

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.merakiapp.*
import com.example.merakiapp.databinding.ActivityGastelugatxeBinding
import com.example.merakiapp.explicaciones.DemoActivity
import com.example.merakiapp.servicios.ServicioAudios

class GaztelugatxeActivity() : AppCompatActivity() {
    lateinit var binding: ActivityGastelugatxeBinding

    // AUDIO Y FONDO
    private var audioSeleccionado = R.raw.gritoninos                    // Audio a reproducir
    private var fondoSeleccionado = R.drawable.fondogaztelugatxe        // Fondo a mostrar
    private var pantallaSeleccionada = "gaztelugatxe"             // Pantalla enlazada al boton Siguiente del próximo Activity
    var estadoAudio = ""

    var correcto1: Boolean = false      // Resultado de la primera pregunta
    var correcto2: Boolean = false      // Resultado de la segunda pregunta
    var correcto3: Boolean = false      // Resultado de la tercera pregunta
    var correcto4: Boolean = false      // Resultado de la cuarta pregunta


    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityGastelugatxeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(this.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false){
            binding.btnVolverGaztelugatxe.visibility = View.VISIBLE
        }else{
            binding.btnVolverGaztelugatxe.visibility = View.GONE
        }

        // -------------------------------- DIALOGS --------------------------------
        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaGaztelugatxe.setOnClickListener {
            val mensaje = mensajeGaztelugatxe
            mostrar_dialog(this, tituloJuegos, mensaje)
        }
        binding.btnInfoPantallaGaztelugatxe.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }
        // -------------------------------------------------------------------------

        // FONDO
        var activityGaztelugatxe = binding.activityGaztelugatxe
        activityGaztelugatxe.background = resources.getDrawable(fondoSeleccionado, theme)

        // Conexión con el Servicio de Audios
        var intent = Intent(this, ServicioAudios::class.java)

        // POR DEFECTO:
        // Ocultar el GIF de los aplausos
        binding.gifAplausos.visibility = ImageView.INVISIBLE
        // Ocultar el botón de Finalizar
        binding.btnFinalizarGaztelugatxe.visibility = Button.GONE


        // CONTROL DE BOTONES
        // Comprobar resultado
        binding.btnComprobarGaztelugatxe.setOnClickListener {
            comprobarRespuestas()
        }

        // Volver a la Activity anterior
        binding.btnVolverGaztelugatxe.setOnClickListener {
            finish()
            stopService(intent)

            audioSeleccionado = R.raw.audiosanjuandegaztelugatxe
            var intent = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent)
        }

        // Finalizar juego
        binding.btnFinalizarGaztelugatxe.setOnClickListener {
            startActivity(Intent(this, MenuNav::class.java))
            this.getSharedPreferences("validar7", 0).edit().putBoolean("validar7", true).apply()
        }

    }

    // Función para comprobar el resultado de las respuestas
    private fun comprobarRespuestas() {

        // Comprobar 1º pregunta
        if (binding.gp1.checkedRadioButtonId == binding.rb1.id) {
            // Si la respuesta es correcta, el fondo no sufrirá ningún cambio
            binding.gp1.setBackgroundColor(Color.TRANSPARENT)
            correcto1 = true
        } else {
            // Si la respuesta es incorrecta, el fondo se volverá rojo
            binding.gp1.setBackgroundColor(Color.RED)
            correcto1 = false
        }

        // Comprobar 2º pregunta
        if (binding.gp2.checkedRadioButtonId == binding.rb6.id) {
            // Si la respuesta es correcta, el fondo no sufrirá ningún cambio
            binding.gp2.setBackgroundColor(Color.TRANSPARENT)
            correcto2 = true
        } else {
            // Si la respuesta es incorrecta, el fondo se volverá rojo
            binding.gp2.setBackgroundColor(Color.RED)
            correcto2 = false
        }

        // Comprobar 3º pregunta
        if (binding.gp3.checkedRadioButtonId == binding.rb8.id) {
            // Si la respuesta es correcta, el fondo no sufrirá ningún cambio
            binding.gp3.setBackgroundColor(Color.TRANSPARENT)
            correcto3 = true
        } else {
            // Si la respuesta es incorrecta, el fondo se volverá rojo
            binding.gp3.setBackgroundColor(Color.RED)
            correcto3 = false
        }

        // Comprobar 4º pregunta
        if (binding.gp4.checkedRadioButtonId == binding.rb11.id) {
            // Si la respuesta es correcta, el fondo no sufrirá ningún cambio
            binding.gp4.setBackgroundColor(Color.TRANSPARENT)
            correcto4 = true
        } else {
            // Si la respuesta es incorrecta, el fondo se volverá rojo
            binding.gp4.setBackgroundColor(Color.RED)
            correcto4 = false
        }

        // Comprobar si todas las respuestas son correctas
        if (correcto1 == true && correcto2 == true && correcto3 == true && correcto4 == true) {
            // Elementos a ocultar
            binding.btnComprobarGaztelugatxe.visibility = Button.GONE
            binding.btnVolverGaztelugatxe.visibility = Button.GONE

            // Elementos a mostrar
            binding.gifAplausos.visibility = ImageView.VISIBLE
            binding.btnFinalizarGaztelugatxe.visibility = Button.VISIBLE

            // Mostrar el GIF de los aplausos
            mostrarGif()

            // Reproducir audio
            estadoAudio = "play"
            iniciarServicioAudio(estadoAudio, audioSeleccionado)
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
        val ImageView: ImageView = binding.gifAplausos
        Glide.with(this).load(R.drawable.aplausos).into(ImageView)
    }
}