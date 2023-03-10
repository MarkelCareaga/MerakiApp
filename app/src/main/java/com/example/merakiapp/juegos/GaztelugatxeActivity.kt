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
import com.example.merakiapp.*
import com.example.merakiapp.databinding.ActivityGastelugatxeBinding
import com.example.merakiapp.explicaciones.Explicaciones
import com.example.merakiapp.listas.ListaDialogos
import com.example.merakiapp.listas.ListaRecursos
import com.example.merakiapp.servicios.ServicioAudios

class GaztelugatxeActivity() : AppCompatActivity(), Explicaciones {
    lateinit var binding: ActivityGastelugatxeBinding

    private var listaDialogos = ListaDialogos()

    var estadoAudio = ""
    var correcto1: Boolean = false      // Resultado de la primera pregunta
    var correcto2: Boolean = false      // Resultado de la segunda pregunta
    var correcto3: Boolean = false      // Resultado de la tercera pregunta
    var correcto4: Boolean = false      // Resultado de la cuarta pregunta


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityGastelugatxeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // ----------------------- BOTONES AYUDA Y ROTACIÓN -----------------------
        // AYUDA
        binding.btnAyudaGaztelugatxe.setOnClickListener {
            val mensaje = ListaRecursos.mensajeGaztelugatxe
            listaDialogos.mostrar_dialog(this, ListaRecursos.tituloJuegos, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaGaztelugatxe.setOnClickListener {
            listaDialogos.mostrar_info_pantalla(this, false)
        }


        // ----------------------AUDIO AL INICIAR EL JUEGO--------------------------
        // Reproducir audio
        estadoAudio = "play"
        // Conexión con el Servicio de Audios
        var intent = Intent(this, ServicioAudios::class.java)


        // -------------------------------------------------------------------------
        // FONDO
        val activityGaztelugatxe = binding.activityGaztelugatxe
        activityGaztelugatxe.background =
            resources.getDrawable(ListaRecursos.fondo_Gaztelugatxe, theme)

        // Ocultar el GIF de los aplausos
        binding.gifAplausos.visibility = ImageView.INVISIBLE

        // Ocultar el botón de Finalizar
        binding.btnFinalizarGaztelugatxe.visibility = Button.GONE


        // ------------------------ CONTROL DE BOTONES ------------------------
        // COMPROBAR RESULTADO
        binding.btnComprobarGaztelugatxe.setOnClickListener {
            comprobarRespuestas()
        }

        // VOLVER
        binding.btnVolverGaztelugatxe.setOnClickListener {
            stopService(intent)
            finish()

            intent = abrirExplicacion(this, ListaRecursos.pantalla_Gaztelugatxe)
            startActivity(intent)
        }

        // FINALIZAR
        binding.btnFinalizarGaztelugatxe.setOnClickListener {
            stopService(intent)
            finish()

            startActivity(Intent(this, MenuNav::class.java))

            // ???
            this.getSharedPreferences("validar7", 0).edit().putBoolean("validar7", true).apply()
        }

    }


    // ---------------------- FUNCIONES ADICIONALES ----------------------
    // Función para comprobar el resultado de las respuestas
    private fun comprobarRespuestas() {

        // Comprobar 1º pregunta
        if (binding.gp1.checkedRadioButtonId == binding.rb1.id) {
            // Si la respuesta es correcta, el fondo no sufrirá ningún cambio
            binding.gp1.setBackgroundColor(Color.TRANSPARENT)
            binding.txtTest1.setTextColor(Color.BLACK)
            binding.rb1.setTextColor(Color.BLACK)
            binding.rb2.setTextColor(Color.BLACK)
            binding.rb3.setTextColor(Color.BLACK)
            correcto1 = true
        } else {
            // Si la respuesta es incorrecta, el fondo se volverá rojo
            binding.gp1.setBackgroundColor(Color.RED)
            binding.txtTest1.setTextColor(Color.WHITE)
            binding.rb1.setTextColor(Color.WHITE)
            binding.rb2.setTextColor(Color.WHITE)
            binding.rb3.setTextColor(Color.WHITE)
            correcto1 = false
        }

        // Comprobar 2º pregunta
        if (binding.gp2.checkedRadioButtonId == binding.rb6.id) {
            // Si la respuesta es correcta, el fondo no sufrirá ningún cambio
            binding.gp2.setBackgroundColor(Color.TRANSPARENT)
            binding.txtTest2.setTextColor(Color.BLACK)
            binding.rb4.setTextColor(Color.BLACK)
            binding.rb5.setTextColor(Color.BLACK)
            binding.rb6.setTextColor(Color.BLACK)
            correcto2 = true
        } else {
            // Si la respuesta es incorrecta, el fondo se volverá rojo
            binding.gp2.setBackgroundColor(Color.RED)
            binding.txtTest2.setTextColor(Color.WHITE)
            binding.rb4.setTextColor(Color.WHITE)
            binding.rb5.setTextColor(Color.WHITE)
            binding.rb6.setTextColor(Color.WHITE)
            correcto2 = false
        }

        // Comprobar 3º pregunta
        if (binding.gp3.checkedRadioButtonId == binding.rb8.id) {
            // Si la respuesta es correcta, el fondo no sufrirá ningún cambio
            binding.gp3.setBackgroundColor(Color.TRANSPARENT)
            binding.txtTest3.setTextColor(Color.BLACK)
            binding.rb7.setTextColor(Color.BLACK)
            binding.rb8.setTextColor(Color.BLACK)
            binding.rb9.setTextColor(Color.BLACK)
            correcto3 = true
        } else {
            // Si la respuesta es incorrecta, el fondo se volverá rojo
            binding.gp3.setBackgroundColor(Color.RED)
            binding.txtTest3.setTextColor(Color.WHITE)
            binding.rb7.setTextColor(Color.WHITE)
            binding.rb8.setTextColor(Color.WHITE)
            binding.rb9.setTextColor(Color.WHITE)
            correcto3 = false
        }

        // Comprobar 4º pregunta
        if (binding.gp4.checkedRadioButtonId == binding.rb11.id) {
            // Si la respuesta es correcta, el fondo no sufrirá ningún cambio
            binding.gp4.setBackgroundColor(Color.TRANSPARENT)
            binding.txtTest4.setTextColor(Color.BLACK)
            binding.rb10.setTextColor(Color.BLACK)
            binding.rb11.setTextColor(Color.BLACK)
            binding.rb12.setTextColor(Color.BLACK)
            correcto4 = true
        } else {
            // Si la respuesta es incorrecta, el fondo se volverá rojo
            binding.gp4.setBackgroundColor(Color.RED)
            binding.txtTest4.setTextColor(Color.WHITE)
            binding.rb10.setTextColor(Color.WHITE)
            binding.rb11.setTextColor(Color.WHITE)
            binding.rb12.setTextColor(Color.WHITE)
            correcto4 = false
        }

        // Comprobar si todas las respuestas son correctas
        if (correcto1 && correcto2 && correcto3 && correcto4) {
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
            iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Gritos)
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
        val imageView: ImageView = binding.gifAplausos
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
        intent = abrirExplicacion(this, ListaRecursos.pantalla_Gaztelugatxe)
        startActivity(intent)
    }
}