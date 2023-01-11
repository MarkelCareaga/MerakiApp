package com.example.merakiapp.juegos

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.merakiapp.*
import com.example.merakiapp.databinding.ActivityFeriaPescadoBinding
import com.example.merakiapp.explicaciones.DemoActivity
import com.example.merakiapp.servicios.ServicioAudios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeriaPescadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeriaPescadoBinding


    // AUDIO Y FONDO
    private var audioSeleccionado = R.raw.gritoninos                    // Audio a reproducir
    private var fondoSeleccionado = R.drawable.fondoferiapescado        // Fondo a mostrar
    private var pantallaSeleccionada = "feria_del_pescado"             // Pantalla enlazada al boton Siguiente del próximo Activity
    var estadoAudio = ""

    // BOTONES A PULSAR DENTRO DEL JUEGO
    private lateinit var btn11: ImageButton
    private lateinit var btn12: ImageButton
    private lateinit var btn21: ImageButton
    private lateinit var btn22: ImageButton
    private lateinit var btn31: ImageButton
    private lateinit var btn32: ImageButton
    private lateinit var btn41: ImageButton
    private lateinit var btn42: ImageButton
    private lateinit var btn51: ImageButton
    private lateinit var btn52: ImageButton

    // BOTONES DE ACCIÓN Y GIF
    private lateinit var gifAplausos: ImageView
    private lateinit var btnVolver: ImageButton
    private lateinit var btnFinalizar: ImageButton
    private lateinit var btnVerResultado: ImageButton

    // VALOR INICIAL ASOCIADO A CADA BOTÓN
    private var boton11 = 11
    private var boton12 = 12
    private var boton21 = 21
    private var boton22 = 22
    private var boton31 = 31
    private var boton32 = 32
    private var boton41 = 41
    private var boton42 = 42
    private var boton51 = 51
    private var boton52 = 52

    // CONTROLAR SI LOS BOTONES HAN SIDO PULSADOS
    private var estado_btn11 = true
    private var estado_btn21 = true
    private var estado_btn31 = true
    private var estado_btn41 = true
    private var estado_btn51 = true

    private var estado_btn12 = true
    private var estado_btn22 = true
    private var estado_btn32 = true
    private var estado_btn42 = true
    private var estado_btn52 = true

    // LISTA Y CONTADOR
    // Lista para asociar valores a cada botón
    private val listaParejas = listOf(1,1,2,2,3,3,4,4,5,5)
    private var contador = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        //Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityFeriaPescadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(this.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false){
            // binding.btnVolverExplicacionBadatoz.visibility = View.VISIBLE
        }else{
            //binding.btnVolverExplicacionBadatoz.visibility = View.GONE
        }

        // -------------------------------- DIALOGS --------------------------------
        // Comprobar si el juego ha sido reiniciado.
        // En dicho caso, mostrará un aviso sobre que el resultado del juego es incorrecto.
        var resultadoJuego = intent.getStringExtra("resultadoJuego").toString()
        if(resultadoJuego == "mal") {
            mostrar_fallo_juego(this)
        }

        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaFeriaPescado.setOnClickListener {
            val mensaje = mensajeFeriaPescado
            mostrar_dialog(this, tituloJuegos, mensaje)
        }
        binding.btnInfoPantallaFeriaPescado.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }
        // -------------------------------------------------------------------------

        // ----------------------AUDIO AL INICIAR EL JUEGO--------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, R.raw.unelasimagenes)
        // -------------------------------------------------------------------------

        // FONDO
        var activityFeriaPescado = binding.activityFeriaPescado
        activityFeriaPescado.background = resources.getDrawable(fondoSeleccionado, theme)

        // AUDIO
        // Conexión con el Servicio de Audios
        var intent = Intent(this, ServicioAudios::class.java)

        // BOTONES
        btn11 = binding.btn11
        btn12 = binding.btn12
        btn21 = binding.btn21
        btn22 = binding.btn22
        btn31 = binding.btn31
        btn32 = binding.btn32
        btn41 = binding.btn41
        btn42 = binding.btn42
        btn51 = binding.btn51
        btn52 = binding.btn52

        // BOTONES SIN FONDO
        btn11.setBackgroundColor(Color.TRANSPARENT)
        btn21.setBackgroundColor(Color.TRANSPARENT)
        btn31.setBackgroundColor(Color.TRANSPARENT)
        btn41.setBackgroundColor(Color.TRANSPARENT)
        btn51.setBackgroundColor(Color.TRANSPARENT)
        btn12.setBackgroundColor(Color.TRANSPARENT)
        btn22.setBackgroundColor(Color.TRANSPARENT)
        btn32.setBackgroundColor(Color.TRANSPARENT)
        btn42.setBackgroundColor(Color.TRANSPARENT)
        btn52.setBackgroundColor(Color.TRANSPARENT)

        // OTROS BOTONES Y GIF
        gifAplausos = binding.gifAplausosFeriaPescado
        btnVolver = binding.btnVolverExplicacionFeriaPescado
        btnFinalizar = binding.btnFinalizarFeriaPescado
        btnVerResultado = binding.btnComprobarFeriaPescado

        // POR DEFECTO:
        // Ocultar el GIF de los aplausos
        gifAplausos.visibility = ImageView.INVISIBLE
        // Ocultar el botón de Finalizar
        btnFinalizar.visibility = Button.GONE

        // CONTROL DE BOTONES
        // Comprobar resultado
        btnVerResultado.setOnClickListener {
            comprobarRespuestas()
        }

        // Volver a la Activity anterior
        btnVolver.setOnClickListener {
            finish()
            stopService(intent)

            audioSeleccionado = R.raw.audioferiadelpescado
            var intent = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent)
        }

        // Finalizar juego
        btnFinalizar.setOnClickListener {
            startActivity(Intent(this, DemoActivity::class.java))
            this.getSharedPreferences("validar3", 0).edit().putBoolean("validar3", true).apply()
        }

        // -------------------------------------------------------------------------

        // UNIR LINEAS
        /*
            Cuando se pulsa un botón:
            1.- El contador aumenta, asociando un valor de la lista a dicho botón.
            2.- Se bloquean los botones de la columna seleccionada.
            3.- En caso de haber pulsado un botón de cada columna, se mostrará su
                correspondiente línea.
         */
        btn11.setOnClickListener {
            contador++
            boton11 = listaParejas[contador]
            controlBotones(btn11)
            controlLineas()
            btn11.setEnabled(false)
            estado_btn11 = false
        }
        btn12.setOnClickListener {
            contador++
            boton12 = listaParejas[contador]
            controlBotones(btn12)
            controlLineas()
            btn12.setEnabled(false)
            estado_btn12 = false
        }
        btn21.setOnClickListener {
            contador++
            boton21 = listaParejas[contador]
            controlBotones(btn21)
            controlLineas()
            btn21.setEnabled(false)
            estado_btn21 = false
        }
        btn22.setOnClickListener {
            contador++
            boton22 = listaParejas[contador]
            controlBotones(btn22)
            controlLineas()
            btn22.setEnabled(false)
            estado_btn22 = false
        }
        btn31.setOnClickListener {
            contador++
            boton31 = listaParejas[contador]
            controlBotones(btn31)
            controlLineas()
            btn31.setEnabled(false)
            estado_btn31 = false
        }
        btn32.setOnClickListener {
            contador++
            boton32 = listaParejas[contador]
            controlBotones(btn32)
            controlLineas()
            btn32.setEnabled(false)
            estado_btn32 = false
        }
        btn41.setOnClickListener {
            contador++
            boton41 = listaParejas[contador]
            controlBotones(btn41)
            controlLineas()
            btn41.setEnabled(false)
            estado_btn41 = false
        }
        btn42.setOnClickListener {
            contador++
            boton42 = listaParejas[contador]
            controlBotones(btn42)
            controlLineas()
            btn42.setEnabled(false)
            estado_btn42 = false
        }
        btn51.setOnClickListener {
            contador++
            boton51 = listaParejas[contador]
            controlBotones(btn51)
            controlLineas()
            btn51.setEnabled(false)
            estado_btn51 = false
        }
        btn52.setOnClickListener {
            contador++
            boton52 = listaParejas[contador]
            controlBotones(btn52)
            controlLineas()
            btn52.setEnabled(false)
            estado_btn52 = false
        }
    }

    // Función para controlar que columna de botones ha sido seleccionada
    private fun controlBotones(btnValor: ImageButton) {
        if (btnValor == btn11 || btnValor == btn21 || btnValor == btn31
            || btnValor == btn41 || btnValor == btn51) {
            estadoBotonesIzquierda(false)
            estadoBotonesDerecha(true)
        } else {
            estadoBotonesIzquierda(true)
            estadoBotonesDerecha(false)
        }
        btnValor.setImageResource(R.drawable.ic_baseline_boton_radio_activado)
    }

    // Función para mostrar las líneas asociadas a cada pareja de botones
    private fun controlLineas() {
        if (boton11 == boton12) dibujarLinea(btn11, btn12)
        if (boton11 == boton22) dibujarLinea(btn11, btn22)
        if (boton11 == boton32) dibujarLinea(btn11, btn32)
        if (boton11 == boton42) dibujarLinea(btn11, btn42)
        if (boton11 == boton52) dibujarLinea(btn11, btn52)

        if (boton21 == boton12) dibujarLinea(btn21, btn12)
        if (boton21 == boton22) dibujarLinea(btn21, btn22)
        if (boton21 == boton32) dibujarLinea(btn21, btn32)
        if (boton21 == boton42) dibujarLinea(btn21, btn42)
        if (boton21 == boton52) dibujarLinea(btn21, btn52)

        if (boton31 == boton12) dibujarLinea(btn31, btn12)
        if (boton31 == boton22) dibujarLinea(btn31, btn22)
        if (boton31 == boton32) dibujarLinea(btn31, btn32)
        if (boton31 == boton42) dibujarLinea(btn31, btn42)
        if (boton31 == boton52) dibujarLinea(btn31, btn52)

        if (boton41 == boton12) dibujarLinea(btn41, btn12)
        if (boton41 == boton22) dibujarLinea(btn41, btn22)
        if (boton41 == boton32) dibujarLinea(btn41, btn32)
        if (boton41 == boton42) dibujarLinea(btn41, btn42)
        if (boton41 == boton52) dibujarLinea(btn41, btn52)

        if (boton51 == boton12) dibujarLinea(btn51, btn12)
        if (boton51 == boton22) dibujarLinea(btn51, btn22)
        if (boton51 == boton32) dibujarLinea(btn51, btn32)
        if (boton51 == boton42) dibujarLinea(btn51, btn42)
        if (boton51 == boton52) dibujarLinea(btn51, btn52)
    }

    // Función para dibujar la linea entre los botones especificados
    private fun dibujarLinea(btnIzquierda: ImageButton, btnDerecha: ImageButton) {
        var layout: RelativeLayout = binding.layoutLineas
        val fondo = Linea(this, btnIzquierda, btnDerecha, 1)
        layout.addView(fondo)
    }

    // ACTIVAR Y DESACTIVAR BOTONES
    // Si el botón ya se ha pulsado (desactivado), no se cambiará su estado
    private fun estadoBotonesIzquierda(estado: Boolean) {
        if (estado_btn11) btn11.setEnabled(estado)
        if (estado_btn21) btn21.setEnabled(estado)
        if (estado_btn31) btn31.setEnabled(estado)
        if (estado_btn41) btn41.setEnabled(estado)
        if (estado_btn51) btn51.setEnabled(estado)
    }

    private fun estadoBotonesDerecha(estado: Boolean) {
        if (estado_btn12) btn12.setEnabled(estado)
        if (estado_btn22) btn22.setEnabled(estado)
        if (estado_btn32) btn32.setEnabled(estado)
        if (estado_btn42) btn42.setEnabled(estado)
        if (estado_btn52) btn52.setEnabled(estado)
    }

    // Función para comprobar el resultado de las respuestas
    private fun comprobarRespuestas() {
        if ((boton11 == boton52) && (boton21 == boton42) && (boton31 == boton22)
            && (boton41 == boton32) && (boton51 == boton12)) {

            // Elementos a ocultar
            btnVolver.visibility = Button.GONE
            btnVerResultado.visibility = Button.GONE

            // Elementos a mostrar
            gifAplausos.visibility = ImageView.VISIBLE
            btnFinalizar.visibility = Button.VISIBLE

            // Mostrar el GIF de los aplausos
            mostrarGif()

            // Reproducir audio
            estadoAudio = "play"
            iniciarServicioAudio(estadoAudio, audioSeleccionado)

        } else {
            // Resetear el juego
            finish()
            startActivity(Intent(this, FeriaPescadoActivity::class.java)
                .putExtra("resultadoJuego", "mal")
            )
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
        val ImageView: ImageView = binding.gifAplausosFeriaPescado
        Glide.with(this).load(R.drawable.aplausos).into(ImageView)
    }
}