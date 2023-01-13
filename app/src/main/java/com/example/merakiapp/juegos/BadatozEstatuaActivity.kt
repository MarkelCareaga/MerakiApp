package com.example.merakiapp.juegos

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.merakiapp.*
import com.example.merakiapp.databinding.ActivityBadatozEstatuaBinding
import com.example.merakiapp.explicaciones.DemoActivity
import com.example.merakiapp.servicios.ServicioAudios

class BadatozEstatuaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBadatozEstatuaBinding

    private lateinit var Imagen : ImageView
    private var audioSeleccionado = 0                           // Audio a reproducir en la siguiente Activity
    private var fondoSeleccionado = R.drawable.fondobadatoz     // Fondo a mostrar en la siguiente Activity
    private var pantallaSeleccionada = "badatoz_estatua"        // Pantalla enlazada al boton Siguiente del próximo Activity
    var estadoAudio = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        //Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityBadatozEstatuaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(this.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false){
            binding.btnVolverExplicacionBadatoz.visibility = View.VISIBLE
        }else{
            binding.btnVolverExplicacionBadatoz.visibility = View.GONE
        }

        // -------------------------------- DIALOGS --------------------------------
        // Comprobar si el juego ha sido reiniciado.
        // En dicho caso, mostrará un aviso sobre que el resultado del juego es incorrecto.
        var resultadoJuego = intent.getStringExtra("resultadoJuego").toString()
        if(resultadoJuego == "mal") {
            mostrar_fallo_juego(this)
        }

        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaBadatoz.setOnClickListener {
            val mensaje = mensajeBadatoz
            mostrar_dialog(this, tituloJuegos, mensaje)
        }
        binding.btnInfoPantallaBadatoz.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }
        // -------------------------------------------------------------------------

        // ----------------------AUDIO AL INICIAR EL JUEGO--------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, R.raw.ahorateneisquecompletarelpuzzle)
        // -------------------------------------------------------------------------

        // DRAG -> Imagenes a mover
        binding.imagen1drag.setOnLongClickListener(longClickListener)
        binding.imagen2drag.setOnLongClickListener(longClickListener)
        binding.imagen3drag.setOnLongClickListener(longClickListener)
        binding.imagen4drag.setOnLongClickListener(longClickListener)
        binding.imagen5drag.setOnLongClickListener(longClickListener)
        binding.imagen6drag.setOnLongClickListener(longClickListener)
        binding.imagen7drag.setOnLongClickListener(longClickListener)
        binding.imagen8drag.setOnLongClickListener(longClickListener)
        binding.imagen9drag.setOnLongClickListener(longClickListener)

        // TARGET -> Destino de las imagenes
        binding.imagen1target.setOnDragListener(dragListener)
        binding.imagen2target.setOnDragListener(dragListener)
        binding.imagen3target.setOnDragListener(dragListener)
        binding.imagen4target.setOnDragListener(dragListener)
        binding.imagen5target.setOnDragListener(dragListener)
        binding.imagen6target.setOnDragListener(dragListener)
        binding.imagen7target.setOnDragListener(dragListener)
        binding.imagen8target.setOnDragListener(dragListener)
        binding.imagen9target.setOnDragListener(dragListener)

        // Por defecto, las imagenes del DRAG no se ven en el TARGET
        binding.imagen1target.alpha = 0.toFloat()
        binding.imagen2target.alpha = 0.toFloat()
        binding.imagen3target.alpha = 0.toFloat()
        binding.imagen4target.alpha = 0.toFloat()
        binding.imagen5target.alpha = 0.toFloat()
        binding.imagen6target.alpha = 0.toFloat()
        binding.imagen7target.alpha = 0.toFloat()
        binding.imagen8target.alpha = 0.toFloat()
        binding.imagen9target.alpha = 0.toFloat()

        // Por defecto:
        // El GIF de los aplausos está oculto
        binding.gifAplausosBadatoz.visibility = ImageView.INVISIBLE
        // El botón Finalizar esta oculto
        binding.btnFinalizarBadatoz.visibility = Button.GONE


        // CONTROL DE BOTONES
        // Comprobar resultado
        binding.btnComprobarBadatoz.setOnClickListener{
            comprobarpuzzle()
        }

        // Volver a la explicación
        binding.btnVolverExplicacionBadatoz.setOnClickListener {
            finish()
            stopService(intent)

            audioSeleccionado = R.raw.audiobadatoz
            var intent = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent)
        }

        // Finalizar juego
        binding.btnFinalizarBadatoz.setOnClickListener {
            startActivity(Intent(this, MenuNav::class.java))
            this.getSharedPreferences("validar2", 0).edit().putBoolean("validar2", true).apply()
        }
    }

    private class MyDragShadowBuilder(val v: View) : View.DragShadowBuilder(v) {

        override fun onProvideShadowMetrics(size: Point, touch: Point) {
            size.set(view.width, view.height)
            touch.set(view.width / 3, view.height / 1)
        }
        override fun onDrawShadow(canvas: Canvas) {
            v.draw(canvas)
            v.setVisibility(ImageView.INVISIBLE)
            //v.setAlpha(0F)
        }
    }

    private val longClickListener = View.OnLongClickListener { v ->
        // ClipData es un tipo complejo que contiene una o más instancias de elementos
        val item = ClipData.Item(v.tag as? CharSequence)

        val dragData = ClipData(
            v.tag as CharSequence,
            // Metadatos que describen el contenido de un archivo ClipData.
            // Proporciona suficiente información para saber si puede manejar ClipData, pero no los datos en sí.
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item
        )

        Imagen = v as ImageView
        val myShadow = MyDragShadowBuilder(v)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(dragData, myShadow,null,0)
        } else {
            v.startDrag(dragData, myShadow,null,0)
        }
        true
    }

    @SuppressLint("Range")
    private val dragListener = View.OnDragListener { v, event ->
        val receiverView: ImageView = v as ImageView

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                Imagen.visibility = View.VISIBLE
                true
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                if (event.clipDescription.label == receiverView.tag as String) {
                    Imagen.visibility = View.GONE
                } else {
                    Imagen.visibility = View.VISIBLE
                }
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_LOCATION -> {
                Imagen.visibility = View.VISIBLE

                true
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                if (event.clipDescription.label == receiverView.tag as String) {
                    Imagen.visibility = View.VISIBLE
                    v.invalidate()
                }
                true
            }

            DragEvent.ACTION_DROP -> {

                if (event.clipDescription.label == receiverView.tag as String) {
                    receiverView.alpha = 255.toFloat()
                    Imagen.visibility = View.GONE
                } else {
                    Imagen.visibility = View.VISIBLE
                }
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {

                true
            } else -> {
                false
            }
        }
    }

    private fun comprobarpuzzle(){
       if (
            binding.imagen1target.getAlpha().toInt() == 255
            && binding.imagen2target.getAlpha().toInt() == 255
            && binding.imagen3target.getAlpha().toInt() == 255
            && binding.imagen4target.getAlpha().toInt() == 255
            && binding.imagen5target.getAlpha().toInt() == 255
            && binding.imagen6target.getAlpha().toInt() == 255
            && binding.imagen7target.getAlpha().toInt() == 255
            && binding.imagen8target.getAlpha().toInt() == 255
            && binding.imagen9target.getAlpha().toInt() == 255) {
                binding.gifAplausosBadatoz.visibility = ImageView.VISIBLE

           mostrarGif()

           audioSeleccionado = R.raw.gritoninos
           estadoAudio = "play"
           iniciarServicioAudio(estadoAudio, audioSeleccionado)

           //binding.textViewStatus.text = getString(R.string.puzzleCompletado)
           // Elementos a ocultar
           binding.btnComprobarBadatoz.visibility = Button.GONE
           binding.btnVolverExplicacionBadatoz.visibility = Button.GONE

           // Elementos a mostrar
           binding.btnFinalizarBadatoz.visibility = Button.VISIBLE

       } else {
           // Resetear el juego
           finish()
           startActivity(Intent(this, BadatozEstatuaActivity::class.java)
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

    private fun mostrarGif() {
        val ImageView: ImageView = binding.gifAplausosBadatoz
        Glide.with(this).load(R.drawable.aplausos).into(ImageView)
    }
}