package com.example.merakiapp.juegos

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.merakiapp.*
import com.example.merakiapp.databinding.ActivityPuertaSanJuanBinding
import com.example.merakiapp.databinding.ActivitySopaLetrasBinding
import com.example.merakiapp.explicaciones.DemoActivity
import com.example.merakiapp.servicios.ServicioAudios

class SopaLetrasActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySopaLetrasBinding

    //booleanos correctos
    var SANFRANTZISKO : Boolean = false
    var SANTABARBARA : Boolean = false
    var SANMIGEL : Boolean = false
    var ERRENTERIA : Boolean = false
    var ERREMEDIO : Boolean = false
    var SANJUAN : Boolean = false
    var BEI : Boolean = false

    //Contador general
    var Cont = 0

    //Contador palabras
    var ContSanfrantzisko = 0
    var ContSantabarbara = 0
    var ContSanmigel = 0
    var ContErrenteria = 0
    var ContErremedio = 0
    var ContSanjuan= 0
    var ContBei = 0

    //Contador Audio
    var ContAudio = 0

    // AUDIO Y FONDO
    private var audioSeleccionado = R.raw.gritoninos                    // Audio a reproducir
    private var fondoSeleccionado = R.drawable.fondopuertasanjuan       // Fondo a mostrar
    private var pantallaSeleccionada = "puerta_de_san_juan"             // Pantalla enlazada al boton Siguiente del próximo Activity
    var estadoAudio = ""

    private var respuesta = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        super.onCreate(savedInstanceState)
        binding = ActivitySopaLetrasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // -------------------------------- DIALOGS --------------------------------
        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaSopaLetras.setOnClickListener {
            val mensaje = mensajeSopaLetras
            mostrar_dialog(this, tituloJuegos, mensaje)
        }
        binding.btnInfoPantallaSopaLetras.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }
        // -------------------------------------------------------------------------

        // ----------------------AUDIO AL INICIAR EL JUEGO--------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, R.raw.buscalos7)
        // -------------------------------------------------------------------------

        // FONDO
        var activityPuertaSanJuan = binding.activitySopaLetras
        activityPuertaSanJuan.background = resources.getDrawable(fondoSeleccionado, theme)

        // Conexión con el Servicio de Audios
        var intent = Intent(this, ServicioAudios::class.java)

        // POR DEFECTO:
        // Ocultar el GIF de los aplausos
        binding.gifAplausosSopaLetras.visibility = ImageView.INVISIBLE
        // Ocultar el botón de Finalizar
        binding.btnFinalizarSopaLetras.visibility = Button.GONE

        // Volver a la Activity anterior
        binding.btnVolverExplicacionSopaLetras.setOnClickListener {
            finish()
            stopService(intent)
        }

        // Finalizar juego
        binding.btnFinalizarSopaLetras.setOnClickListener {
            //startActivity(Intent(this, SopaLetrasActivity::class.java))
            startActivity(Intent(this, DemoActivity::class.java))
            this.getSharedPreferences("validar1", 0).edit().putBoolean("validar1", true).apply()
        }

        // Obtiene la vista del GridLayout
        val gridLayout = binding.gridLayout
        //val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        //SANFRANTZISKO
        val Sanfrantzisko = binding.Sanfrantzisko
        val sAnfrantzisko = binding.sAnfrantzisko
        val saNfrantzisko = binding.saNfrantzisko
        val sanFrantzisko = binding.sanFrantzisko
        val sanfRantzisko = binding.sanfRantzisko
        val sanfrAntzisko = binding.sanfrAntzisko
        val sanfraNtzisko = binding.sanfraNtzisko
        val sanfranTzisko = binding.sanfranTzisko
        val sanfrantZisko = binding.sanfrantZisko
        val sanfrantzIsko = binding.sanfrantzIsko
        val sanfrantziSko = binding.sanfrantziSko
        val sanfrantzisKo = binding.sanfrantzisKo
        val sanfrantziskO = binding.sanfrantziskO

        //SANTABARBARA
        val Santabarbara = binding.Santabarbara
        val sAntabarbara = binding.sAntabarbara
        val saNtabarbara = binding.saNtabarbara
        val sanTabarbara = binding.sanTabarbara
        val santAbarbara = binding.santAbarbara
        val santaBarbara = binding.santaBarbara
        val santabArbara = binding.santabArbara
        val santabaRbara = binding.santabaRbara
        val santabarBara = binding.santabarBara
        val santabarbAra = binding.santabarbAra
        val santabarbaRa = binding.santabarbaRa
        val santabarbarA = binding.santabarbarA

        //SANMIGEL
        val Sanmigel = binding.Sanmigel
        val sAnmigel = binding.sAnmigel
        val saNmigel = binding.saNmigel
        val sanMigel = binding.sanMigel
        val sanmIgel = binding.sanmIgel
        val sanmiGel = binding.sanmiGel
        val sanmigEl = binding.sanmigEl
        val sanmigeL = binding.sanmigeL

        //ERRENTERIA
        val Errenteria = binding.Errenteria
        val eRrenteria = binding.eRrenteria
        val erRenteria = binding.erRenteria
        val errEnteria = binding.errEnteria
        val erreNteria = binding.erreNteria
        val errenTeria = binding.errenTeria
        val errentEria = binding.errentEria
        val errenteRia = binding.errenteRia
        val errenterIa = binding.errenterIa
        val errenteriA = binding.errenteriA

        //ERREMEDIO
        val Erremedio = binding.Erremedio
        val eRremedio = binding.eRremedio
        val erRemedio = binding.erRemedio
        val errEmedio = binding.errEmedio
        val erreMedio = binding.erreMedio
        val erremEdio = binding.erremEdio
        val erremeDio = binding.erremeDio
        val erremedIo = binding.erremedIo
        val erremediO = binding.erremediO

        //SANJUAN
        val Sanjuan = binding.Sanjuan
        val sAnjuan = binding.sAnjuan
        val saNjuan = binding.saNjuan
        val sanJuan = binding.sanJuan
        val sanjUan = binding.sanjUan
        val sanjuAn = binding.sanjuAn
        val sanjuaN = binding.sanjuaN

        //BEI
        val Bei = binding.Bei
        val bEi = binding.bEi
        val beI = binding.beI

        fun Comprobarpalabras(){

            if(SANFRANTZISKO && SANTABARBARA && SANMIGEL && ERRENTERIA && ERREMEDIO && SANJUAN && BEI){

                ContAudio++

                if (ContAudio == 7){
                    Toast.makeText(this, "Felicidades", Toast.LENGTH_SHORT).show()

                    // Elementos a ocultar
                    binding.btnVolverExplicacionSopaLetras.visibility = Button.GONE

                    // Elementos a mostrar
                    binding.gifAplausosSopaLetras.visibility = ImageView.VISIBLE
                    binding.btnFinalizarSopaLetras.visibility = Button.VISIBLE

                    // Mostrar el GIF de los aplausos
                    mostrarGif()

                    // Reproducir audio
                    estadoAudio = "play"
                    iniciarServicioAudio(estadoAudio, audioSeleccionado)
                }
            }

        }

        fun CambiarColor(){

            if(SANFRANTZISKO){
                // Pon el color verde
                Sanfrantzisko.setBackgroundColor(Color.GREEN)
                sAnfrantzisko.setBackgroundColor(Color.GREEN)
                saNfrantzisko.setBackgroundColor(Color.GREEN)
                sanFrantzisko.setBackgroundColor(Color.GREEN)
                sanfRantzisko.setBackgroundColor(Color.GREEN)
                sanfrAntzisko.setBackgroundColor(Color.GREEN)
                sanfraNtzisko.setBackgroundColor(Color.GREEN)
                sanfranTzisko.setBackgroundColor(Color.GREEN)
                sanfrantZisko.setBackgroundColor(Color.GREEN)
                sanfrantzIsko.setBackgroundColor(Color.GREEN)
                sanfrantziSko.setBackgroundColor(Color.GREEN)
                sanfrantzisKo.setBackgroundColor(Color.GREEN)
                sanfrantziskO.setBackgroundColor(Color.GREEN)

                binding.textInfSanfrantzisko.setTextColor(Color.GREEN)
                binding.textInfSanfrantzisko.setTypeface(binding.textInfSanfrantzisko.typeface, Typeface.BOLD)

                Comprobarpalabras()
            }
            if(SANTABARBARA){

                // Pon el color verde
                Santabarbara.setBackgroundColor(Color.GREEN)
                sAntabarbara.setBackgroundColor(Color.GREEN)
                saNtabarbara.setBackgroundColor(Color.GREEN)
                sanTabarbara.setBackgroundColor(Color.GREEN)
                santAbarbara.setBackgroundColor(Color.GREEN)
                santaBarbara.setBackgroundColor(Color.GREEN)
                santabArbara.setBackgroundColor(Color.GREEN)
                santabaRbara.setBackgroundColor(Color.GREEN)
                santabarBara.setBackgroundColor(Color.GREEN)
                santabarbAra.setBackgroundColor(Color.GREEN)
                santabarbaRa.setBackgroundColor(Color.GREEN)
                santabarbarA.setBackgroundColor(Color.GREEN)

                binding.textInfSantabarbara.setTextColor(Color.GREEN)
                binding.textInfSantabarbara.setTypeface(binding.textInfSantabarbara.typeface, Typeface.BOLD)

                Comprobarpalabras()

            }
            if(SANMIGEL){

                Sanmigel.setBackgroundColor(Color.GREEN)
                sAnmigel.setBackgroundColor(Color.GREEN)
                saNmigel.setBackgroundColor(Color.GREEN)
                sanMigel.setBackgroundColor(Color.GREEN)
                sanmIgel.setBackgroundColor(Color.GREEN)
                sanmiGel.setBackgroundColor(Color.GREEN)
                sanmigEl.setBackgroundColor(Color.GREEN)
                sanmigeL.setBackgroundColor(Color.GREEN)

                binding.textInfSanmigel.setTextColor(Color.GREEN)
                binding.textInfSanmigel.setTypeface(binding.textInfSanmigel.typeface, Typeface.BOLD)

                Comprobarpalabras()

            }
            if(ERRENTERIA){

                // Pon el color verde
                Errenteria.setBackgroundColor(Color.GREEN)
                eRrenteria.setBackgroundColor(Color.GREEN)
                erRenteria.setBackgroundColor(Color.GREEN)
                errEnteria.setBackgroundColor(Color.GREEN)
                erreNteria.setBackgroundColor(Color.GREEN)
                errenTeria.setBackgroundColor(Color.GREEN)
                errentEria.setBackgroundColor(Color.GREEN)
                errenteRia.setBackgroundColor(Color.GREEN)
                errenterIa.setBackgroundColor(Color.GREEN)
                errenteriA.setBackgroundColor(Color.GREEN)

                binding.textInfErrenteria.setTextColor(Color.GREEN)
                binding.textInfErrenteria.setTypeface(binding.textInfErrenteria.typeface, Typeface.BOLD)

                Comprobarpalabras()

            }
            if(ERREMEDIO){

                // Pon el color verde
                Erremedio.setBackgroundColor(Color.GREEN)
                eRremedio.setBackgroundColor(Color.GREEN)
                erRemedio.setBackgroundColor(Color.GREEN)
                errEmedio.setBackgroundColor(Color.GREEN)
                erreMedio.setBackgroundColor(Color.GREEN)
                erremEdio.setBackgroundColor(Color.GREEN)
                erremeDio.setBackgroundColor(Color.GREEN)
                erremedIo.setBackgroundColor(Color.GREEN)
                erremediO.setBackgroundColor(Color.GREEN)

                binding.textInfErremedio.setTextColor(Color.GREEN)
                binding.textInfErremedio.setTypeface(binding.textInfErremedio.typeface, Typeface.BOLD)

                Comprobarpalabras()

            }
            if(SANJUAN){

                // Pon el color verde
                Sanjuan.setBackgroundColor(Color.GREEN)
                sAnjuan.setBackgroundColor(Color.GREEN)
                saNjuan.setBackgroundColor(Color.GREEN)
                sanJuan.setBackgroundColor(Color.GREEN)
                sanjUan.setBackgroundColor(Color.GREEN)
                sanjuAn.setBackgroundColor(Color.GREEN)
                sanjuaN.setBackgroundColor(Color.GREEN)

                binding.textInfSanjuan.setTextColor(Color.GREEN)
                binding.textInfSanjuan.setTypeface(binding.textInfSanjuan.typeface, Typeface.BOLD)

                Comprobarpalabras()

            }
            if(BEI){

                // Pon el color verde
                Bei.setBackgroundColor(Color.GREEN)
                bEi.setBackgroundColor(Color.GREEN)
                beI.setBackgroundColor(Color.GREEN)

                binding.textInfBei.setTextColor(Color.GREEN)
                binding.textInfBei.setTypeface(binding.textInfBei.typeface, Typeface.BOLD)

                Comprobarpalabras()

            }
        }

        gridLayout.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    // Recorre todos los TextView del GridLayout y cambia su color si el dedo está encima
                    for (i in 0 until gridLayout.childCount) {
                        val textView = gridLayout.getChildAt(i) as TextView
                        if (isPointInsideView(event.rawX, event.rawY, textView)) {
                            val backgroundDrawable = textView.background
                            if (backgroundDrawable !is ColorDrawable || (backgroundDrawable.color != Color.GREEN && backgroundDrawable.color != Color.CYAN)) {
                                // Set the TextView's background color to red
                                textView.setBackgroundColor(Color.CYAN)
                                Cont++
                                when (textView) {
                                    binding.Sanfrantzisko, binding.sAnfrantzisko, binding.saNfrantzisko, binding.sanFrantzisko, binding.sanfRantzisko, binding.sanfrAntzisko, binding.sanfraNtzisko, binding.sanfranTzisko, binding.sanfrantZisko, binding.sanfrantzIsko, binding.sanfrantziSko, binding.sanfrantzisKo, binding.sanfrantziskO -> {
                                        ContSanfrantzisko++
                                    }
                                }
                                when (textView) {
                                    binding.Santabarbara, binding.sAntabarbara, binding.saNtabarbara, binding.sanTabarbara, binding.santAbarbara, binding.santaBarbara, binding.santabArbara, binding.santabaRbara, binding.santabarBara, binding.santabarbAra, binding.santabarbaRa, binding.santabarbarA -> {
                                        ContSantabarbara++
                                    }
                                }
                                when (textView) {
                                    binding.Sanmigel, binding.sAnmigel, binding.saNmigel, binding.sanMigel, binding.sanmIgel, binding.sanmiGel, binding.sanmigEl, binding.sanmigeL -> {
                                        ContSanmigel++
                                    }
                                }
                                when (textView) {
                                    binding.Errenteria, binding.eRrenteria, binding.erRenteria, binding.errEnteria, binding.erreNteria, binding.errenTeria, binding.errentEria, binding.errenteRia, binding.errenterIa, binding.errenteriA -> {
                                        ContErrenteria++
                                    }
                                }
                                when (textView) {
                                    binding.Erremedio, binding.eRremedio, binding.erRemedio, binding.errEmedio, binding.erreMedio, binding.erremEdio, binding.erremeDio, binding.erremedIo, binding.erremediO -> {
                                        ContErremedio++
                                    }
                                }
                                when (textView) {
                                    binding.Sanjuan, binding.sAnjuan, binding.saNjuan, binding.sanJuan, binding.sanjUan, binding.sanjuAn, binding.sanjuaN -> {
                                        ContSanjuan++
                                    }
                                }
                                when (textView) {
                                    binding.Bei, binding.bEi, binding.beI -> {
                                        ContBei ++
                                    }
                                }
                            }
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    // Recorre todos los TextViews y si no están pintados de verde, los devuelve a su color original
                    for (i in 0 until gridLayout.childCount) {
                        val textView = gridLayout.getChildAt(i) as TextView
                        val backgroundDrawable = textView.background
                        if (backgroundDrawable !is ColorDrawable || backgroundDrawable.color != Color.GREEN) {
                            textView.setBackground(null)

                            if (ContSanfrantzisko == 13 && Cont == 13) {
                                SANFRANTZISKO = true

                                CambiarColor()

                            }
                            if (ContSantabarbara == 12 && Cont == 12) {
                                SANTABARBARA = true

                                CambiarColor()

                            }
                            if (ContSanmigel == 8 && Cont == 8) {
                                SANMIGEL = true

                                CambiarColor()

                            }
                            if (ContErrenteria == 10 && Cont == 10) {
                                ERRENTERIA = true

                                CambiarColor()

                            }
                            if (ContErremedio == 9 && Cont == 9) {
                                ERREMEDIO = true

                                CambiarColor()

                            }
                            if (ContSanjuan == 7 && Cont == 7) {
                                SANJUAN = true

                                CambiarColor()

                            }
                            if (ContBei == 3 && Cont == 3) {
                                BEI = true

                                CambiarColor()
                            }

                            Cont = 0

                            ContSanfrantzisko = 0
                            ContSantabarbara = 0
                            ContSanmigel = 0
                            ContErrenteria = 0
                            ContErremedio = 0
                            ContSanjuan= 0
                            ContBei = 0

                        }
                    }
                }
            }
            // Devuelve verdadero para consumir el evento de touch y evitar que los TextViews se vuelvan a pintar
            true
        }

    }
    fun isPointInsideView(x: Float, y: Float, view: View): Boolean {

        //Punto específico que se toca
        val point = Point(x.toInt(), y.toInt())

        //Obtiene la vista rectangular del widget
        val rect = Rect()
        view.getGlobalVisibleRect(rect)

        //Asigna tamaño del widget a la región
        val widgetRegion = Region()
        widgetRegion.set(rect)

        //Si el punto tocado está dentro del widget, devuelve verdadero
        return widgetRegion.contains(point.x, point.y)
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
        val ImageView: ImageView = binding.gifAplausosSopaLetras
        Glide.with(this).load(R.drawable.aplausos).into(ImageView)
    }
}