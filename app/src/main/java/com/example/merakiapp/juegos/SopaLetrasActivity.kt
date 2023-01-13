package com.example.merakiapp.juegos

import android.annotation.SuppressLint
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
    var estadoAudio = ""
    private var respuesta = 0
    
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Deshabilitar menu superior
        supportActionBar?.hide()

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
            startActivity(Intent(this, MenuNav::class.java))
            this.getSharedPreferences("validar1", 0).edit().putBoolean("validar1", true).apply()
        }

        // Obtiene la vista del GridLayout
        val gridLayout = binding.gridLayout

        //Comprobar si todas las palabras son correctas
        fun Comprobarpalabras(){
            //Si todos los booleanos de las palabras estan en TRUE:
            if(SANFRANTZISKO && SANTABARBARA && SANMIGEL && ERRENTERIA && ERREMEDIO && SANJUAN && BEI){

                //Suma 1 al contador del audio
                ContAudio++

                //Si el ContAudio es igual a 7 haz:
                if (ContAudio == 7){

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

        //Cambiar
        fun CambiarColor(){

            //Si SANFRANTZISKO es TRUE
            if(SANFRANTZISKO){
                // Pon el color verde
                binding.Sanfrantzisko.setBackgroundColor(Color.GREEN)
                binding.sAnfrantzisko.setBackgroundColor(Color.GREEN)
                binding.saNfrantzisko.setBackgroundColor(Color.GREEN)
                binding.sanFrantzisko.setBackgroundColor(Color.GREEN)
                binding.sanfRantzisko.setBackgroundColor(Color.GREEN)
                binding.sanfrAntzisko.setBackgroundColor(Color.GREEN)
                binding.sanfraNtzisko.setBackgroundColor(Color.GREEN)
                binding.sanfranTzisko.setBackgroundColor(Color.GREEN)
                binding.sanfrantZisko.setBackgroundColor(Color.GREEN)
                binding.sanfrantzIsko.setBackgroundColor(Color.GREEN)
                binding.sanfrantziSko.setBackgroundColor(Color.GREEN)
                binding.sanfrantzisKo.setBackgroundColor(Color.GREEN)
                binding.sanfrantziskO.setBackgroundColor(Color.GREEN)

                binding.textInfSanfrantzisko.setTextColor(Color.GREEN)
                binding.textInfSanfrantzisko.setTypeface(binding.textInfSanfrantzisko.typeface, Typeface.BOLD)

                //Llama a la funcion para comprobrobar todas las palabras
                Comprobarpalabras()
            }

            //Si SANTABARBARA es TRUE
            if(SANTABARBARA){

                // Pon el color verde
                binding.Santabarbara.setBackgroundColor(Color.GREEN)
                binding.sAntabarbara.setBackgroundColor(Color.GREEN)
                binding.saNtabarbara.setBackgroundColor(Color.GREEN)
                binding.sanTabarbara.setBackgroundColor(Color.GREEN)
                binding.santAbarbara.setBackgroundColor(Color.GREEN)
                binding.santaBarbara.setBackgroundColor(Color.GREEN)
                binding.santabArbara.setBackgroundColor(Color.GREEN)
                binding.santabaRbara.setBackgroundColor(Color.GREEN)
                binding.santabarBara.setBackgroundColor(Color.GREEN)
                binding.santabarbAra.setBackgroundColor(Color.GREEN)
                binding.santabarbaRa.setBackgroundColor(Color.GREEN)
                binding.santabarbarA.setBackgroundColor(Color.GREEN)

                binding.textInfSantabarbara.setTextColor(Color.GREEN)
                binding.textInfSantabarbara.setTypeface(binding.textInfSantabarbara.typeface, Typeface.BOLD)

                //Llama a la funcion para comprobrobar todas las palabras
                Comprobarpalabras()

            }
            //Si SANMIGEL es TRUE
            if(SANMIGEL){

                binding.Sanmigel.setBackgroundColor(Color.GREEN)
                binding.sAnmigel.setBackgroundColor(Color.GREEN)
                binding.saNmigel.setBackgroundColor(Color.GREEN)
                binding.sanMigel.setBackgroundColor(Color.GREEN)
                binding.sanmIgel.setBackgroundColor(Color.GREEN)
                binding.sanmiGel.setBackgroundColor(Color.GREEN)
                binding.sanmigEl.setBackgroundColor(Color.GREEN)
                binding.sanmigeL.setBackgroundColor(Color.GREEN)

                binding.textInfSanmigel.setTextColor(Color.GREEN)
                binding.textInfSanmigel.setTypeface(binding.textInfSanmigel.typeface, Typeface.BOLD)

                //Llama a la funcion para comprobrobar todas las palabras
                Comprobarpalabras()

            }
            //Si ERRENTERIA es TRUE
            if(ERRENTERIA){

                // Pon el color verde
                binding.Errenteria.setBackgroundColor(Color.GREEN)
                binding.eRrenteria.setBackgroundColor(Color.GREEN)
                binding.erRenteria.setBackgroundColor(Color.GREEN)
                binding.errEnteria.setBackgroundColor(Color.GREEN)
                binding.erreNteria.setBackgroundColor(Color.GREEN)
                binding.errenTeria.setBackgroundColor(Color.GREEN)
                binding.errentEria.setBackgroundColor(Color.GREEN)
                binding.errenteRia.setBackgroundColor(Color.GREEN)
                binding.errenterIa.setBackgroundColor(Color.GREEN)
                binding.errenteriA.setBackgroundColor(Color.GREEN)

                binding.textInfErrenteria.setTextColor(Color.GREEN)
                binding.textInfErrenteria.setTypeface(binding.textInfErrenteria.typeface, Typeface.BOLD)

                //Llama a la funcion para comprobrobar todas las palabras
                Comprobarpalabras()

            }
            //Si ERREMEDIO es TRUE
            if(ERREMEDIO){

                // Pon el color verde
                binding.Erremedio.setBackgroundColor(Color.GREEN)
                binding.eRremedio.setBackgroundColor(Color.GREEN)
                binding.erRemedio.setBackgroundColor(Color.GREEN)
                binding.errEmedio.setBackgroundColor(Color.GREEN)
                binding.erreMedio.setBackgroundColor(Color.GREEN)
                binding.erremEdio.setBackgroundColor(Color.GREEN)
                binding.erremeDio.setBackgroundColor(Color.GREEN)
                binding.erremedIo.setBackgroundColor(Color.GREEN)
                binding.erremediO.setBackgroundColor(Color.GREEN)

                binding.textInfErremedio.setTextColor(Color.GREEN)
                binding.textInfErremedio.setTypeface(binding.textInfErremedio.typeface, Typeface.BOLD)

                //Llama a la funcion para comprobrobar todas las palabras
                Comprobarpalabras()

            }
            //Si SANJUAN es TRUE
            if(SANJUAN){

                // Pon el color verde
                binding.Sanjuan.setBackgroundColor(Color.GREEN)
                binding.sAnjuan.setBackgroundColor(Color.GREEN)
                binding.saNjuan.setBackgroundColor(Color.GREEN)
                binding.sanJuan.setBackgroundColor(Color.GREEN)
                binding.sanjUan.setBackgroundColor(Color.GREEN)
                binding.sanjuAn.setBackgroundColor(Color.GREEN)
                binding.sanjuaN.setBackgroundColor(Color.GREEN)

                binding.textInfSanjuan.setTextColor(Color.GREEN)
                binding.textInfSanjuan.setTypeface(binding.textInfSanjuan.typeface, Typeface.BOLD)

                //Llama a la funcion para comprobrobar todas las palabras
                Comprobarpalabras()

            }
            //Si BEI es TRUE
            if(BEI){

                // Pon el color verde
                binding.Bei.setBackgroundColor(Color.GREEN)
                binding.bEi.setBackgroundColor(Color.GREEN)
                binding.beI.setBackgroundColor(Color.GREEN)

                binding.textInfBei.setTextColor(Color.GREEN)
                binding.textInfBei.setTypeface(binding.textInfBei.typeface, Typeface.BOLD)

                //Llama a la funcion para comprobrobar todas las palabras
                Comprobarpalabras()

            }
        }

        //Cuando presionamos algun textview del gridlyout
        gridLayout.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    // Recorre todos los TextView del GridLayout
                    for (i in 0 until gridLayout.childCount) {
                        val textView = gridLayout.getChildAt(i) as TextView
                        //Recoger las coordenadas de los textviews pulsados por el usuario
                        if (isPointInsideView(event.rawX, event.rawY, textView)) {
                            val backgroundDrawable = textView.background
                            //Se comprueba que el background de los textviews que estamos seleccionando no estes ya pintados de otro color
                            if (backgroundDrawable !is ColorDrawable || (backgroundDrawable.color != Color.GREEN && backgroundDrawable.color != Color.CYAN)) {
                                //Si Cont es menor que 13
                                if(Cont < 13){
                                    // Pinta el background de color CYAN
                                    textView.setBackgroundColor(Color.CYAN)
                                    //Suma 1 a Cont
                                    Cont++
                                    //Cuando el usuario presiona cualquier TextView del gridlyout perteneciente a SANFRANTSIKO
                                    when (textView) {
                                        binding.Sanfrantzisko, binding.sAnfrantzisko, binding.saNfrantzisko, binding.sanFrantzisko, binding.sanfRantzisko, binding.sanfrAntzisko, binding.sanfraNtzisko, binding.sanfranTzisko, binding.sanfrantZisko, binding.sanfrantzIsko, binding.sanfrantziSko, binding.sanfrantzisKo, binding.sanfrantziskO -> {
                                            //Suma 1 al contador de SANFRANTSIKO
                                            ContSanfrantzisko++
                                        }
                                    }
                                    //Cuando el usuario presiona cualquier TextView del gridlyout perteneciente a SANTABARBARA
                                    when (textView) {
                                        binding.Santabarbara, binding.sAntabarbara, binding.saNtabarbara, binding.sanTabarbara, binding.santAbarbara, binding.santaBarbara, binding.santabArbara, binding.santabaRbara, binding.santabarBara, binding.santabarbAra, binding.santabarbaRa, binding.santabarbarA -> {
                                            //Suma 1 al contador de SANTABARBARA
                                            ContSantabarbara++
                                        }
                                    }
                                    //Cuando el usuario presiona cualquier TextView del gridlyout perteneciente a SANMIGEL
                                    when (textView) {
                                        binding.Sanmigel, binding.sAnmigel, binding.saNmigel, binding.sanMigel, binding.sanmIgel, binding.sanmiGel, binding.sanmigEl, binding.sanmigeL -> {
                                            //Suma 1 al contador de SANMIGEL
                                            ContSanmigel++
                                        }
                                    }
                                    //Cuando el usuario presiona cualquier TextView del gridlyout perteneciente a ERRENTERIA
                                    when (textView) {
                                        binding.Errenteria, binding.eRrenteria, binding.erRenteria, binding.errEnteria, binding.erreNteria, binding.errenTeria, binding.errentEria, binding.errenteRia, binding.errenterIa, binding.errenteriA -> {
                                            //Suma 1 al contador de ERRENTERIA
                                            ContErrenteria++
                                        }
                                    }
                                    //Cuando el usuario presiona cualquier TextView del gridlyout perteneciente a ERREMEDIO
                                    when (textView) {
                                        binding.Erremedio, binding.eRremedio, binding.erRemedio, binding.errEmedio, binding.erreMedio, binding.erremEdio, binding.erremeDio, binding.erremedIo, binding.erremediO -> {
                                            //Suma 1 al contador de ERREMEDIO
                                            ContErremedio++
                                        }
                                    }
                                    //Cuando el usuario presiona cualquier TextView del gridlyout perteneciente a SANJUAN
                                    when (textView) {
                                        binding.Sanjuan, binding.sAnjuan, binding.saNjuan, binding.sanJuan, binding.sanjUan, binding.sanjuAn, binding.sanjuaN -> {
                                            //Suma 1 al contador de SANJUAN
                                            ContSanjuan++
                                        }
                                    }
                                    //Cuando el usuario presiona cualquier TextView del gridlyout perteneciente a BEI
                                    when (textView) {
                                        binding.Bei, binding.bEi, binding.beI -> {
                                            //Suma 1 al contador de BEI
                                            ContBei ++
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //Cuando dejamos de presionar cualquir TextView y levantamos el dedo de la pantalla
                MotionEvent.ACTION_UP -> {
                    // Recorre todos los TextViews
                    for (i in 0 until gridLayout.childCount) {
                        val textView = gridLayout.getChildAt(i) as TextView
                        val backgroundDrawable = textView.background
                        //Comprueba si los textviews son de color diferente a verde
                        if (backgroundDrawable !is ColorDrawable || backgroundDrawable.color != Color.GREEN) {
                            //Quita el color de background de los textviews
                            textView.setBackground(null)
                            //SI el contador de SANFRANTZISKO y el CONT general es igual a 13
                            if (ContSanfrantzisko == 13 && Cont == 13) {
                                //SANFRANTZISKO se pone a valor TRUE
                                SANFRANTZISKO = true
                                //Se llama a la funcion CambiarColor para cambiar a verde el color de los textviews seleccionados
                                CambiarColor()

                            }
                            //SI el contador de SANTABARBARA y el CONT general es igual a 12
                            if (ContSantabarbara == 12 && Cont == 12) {
                                //SANFRANTZISKO se pone a valor TRUE
                                SANTABARBARA = true
                                //Se llama a la funcion CambiarColor para cambiar a verde el color de los textviews seleccionados
                                CambiarColor()

                            }
                            //SI el contador de SANMIGEL y el CONT general es igual a 8
                            if (ContSanmigel == 8 && Cont == 8) {
                                //SANFRANTZISKO se pone a valor TRUE
                                SANMIGEL = true
                                //Se llama a la funcion CambiarColor para cambiar a verde el color de los textviews seleccionados
                                CambiarColor()

                            }
                            //SI el contador de ERRENTERIA y el CONT general es igual a 10
                            if (ContErrenteria == 10 && Cont == 10) {
                                //SANFRANTZISKO se pone a valor TRUE
                                ERRENTERIA = true
                                //Se llama a la funcion CambiarColor para cambiar a verde el color de los textviews seleccionados
                                CambiarColor()

                            }
                            //SI el contador de ERREMEDIO y el CONT general es igual a 9
                            if (ContErremedio == 9 && Cont == 9) {
                                //SANFRANTZISKO se pone a valor TRUE
                                ERREMEDIO = true
                                //Se llama a la funcion CambiarColor para cambiar a verde el color de los textviews seleccionados
                                CambiarColor()

                            }
                            //SI el contador de SANJUAN y el CONT general es igual a 7
                            if (ContSanjuan == 7 && Cont == 7) {
                                //SANFRANTZISKO se pone a valor TRUE
                                SANJUAN = true
                                //Se llama a la funcion CambiarColor para cambiar a verde el color de los textviews seleccionados
                                CambiarColor()

                            }
                            //SI el contador de BEI y el CONT general es igual a 3
                            if (ContBei == 3 && Cont == 3) {
                                //SANFRANTZISKO se pone a valor TRUE
                                BEI = true
                                //Se llama a la funcion CambiarColor para cambiar a verde el color de los textviews seleccionados
                                CambiarColor()
                            }

                            // Se resetea todos los contadores a 0
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