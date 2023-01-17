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
import com.example.merakiapp.Dialogos.Companion.mensajeXixili
import com.example.merakiapp.Dialogos.Companion.tituloJuegos
import com.example.merakiapp.databinding.ActivityXixiliBinding
import com.example.merakiapp.explicaciones.DemoActivity
import com.example.merakiapp.servicios.ServicioAudios

class XixiliActivity : AppCompatActivity(), Dialogos {
    private lateinit var binding: ActivityXixiliBinding

    // AUDIO Y FONDO
    private var audioSeleccionado = R.raw.gritoninos            // Audio a reproducir
    private var fondoSeleccionado = R.drawable.fondoxixili      // Fondo a mostrar
    private var pantallaSeleccionada = "xixili"             // Pantalla enlazada al boton Siguiente del próximo Activity
    var estadoAudio = ""

    private lateinit var Imagen : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityXixiliBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(this.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false){
            binding.btnVolverExplicacionXixili.visibility = View.VISIBLE
        }else{
            binding.btnVolverExplicacionXixili.visibility = View.GONE
        }

        // -------------------------------- DIALOGS --------------------------------
        // Comprobar si el juego ha sido reiniciado.
        // En dicho caso, mostrará un aviso sobre que el resultado del juego es incorrecto.
        var resultadoJuego = intent.getStringExtra("resultadoJuego").toString()
        if(resultadoJuego == "mal") {
            mostrar_fallo_juego(this)
        }

        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaXixili.setOnClickListener {
            val mensaje = mensajeXixili
            mostrar_dialog(this, tituloJuegos, mensaje)
        }
        binding.btnInfoPantallaXixili.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }
        // -------------------------------------------------------------------------

        // ----------------------AUDIO AL INICIAR EL JUEGO--------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, R.raw.ahoraostoca)
        // -------------------------------------------------------------------------

        // FONDO
        var activityXixili = binding.activityXixili
        activityXixili.background = resources.getDrawable(fondoSeleccionado, theme)

        // DRAG -> Imagenes a mover
        binding.imgTexto1.setOnLongClickListener(longClickListener)
        binding.imgTexto2.setOnLongClickListener(longClickListener)
        binding.imgTexto3.setOnLongClickListener(longClickListener)
        binding.imgTexto4.setOnLongClickListener(longClickListener)
        binding.imgTextoMal1.setOnLongClickListener(longClickListener)
        binding.imgTextoMal2.setOnLongClickListener(longClickListener)

        // TARGET -> Destino de las imagenes
        binding.imgTexto1original.setOnDragListener(dragListener)
        binding.imgTexto2original.setOnDragListener(dragListener)
        binding.imgTexto3original.setOnDragListener(dragListener)
        binding.imgTexto4original.setOnDragListener(dragListener)

        // Por defecto, las imagenes del DRAG no se ven en el TARGET
        binding.imgTexto1original.alpha = 0.toFloat()
        binding.imgTexto2original.alpha = 0.toFloat()
        binding.imgTexto3original.alpha = 0.toFloat()
        binding.imgTexto4original.alpha = 0.toFloat()


        // Por defecto:
        // El GIF de los aplausos está oculto
        binding.gifAplausosXixili.visibility = ImageView.INVISIBLE
        // El botón Finalizar esta oculto
        binding.btnFinalizarXixili.visibility = Button.GONE

        // CONTROL DE BOTONES
        // Comprobar resultado
        binding.btnComprobarXixili.setOnClickListener{
            comprobarpuzzle()
        }

        // Volver a la explicación
        binding.btnVolverExplicacionXixili.setOnClickListener {
            finish()
            stopService(intent)

            audioSeleccionado = R.raw.audioxixili
            var intent = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent)
        }

        // Finalizar juego
        binding.btnFinalizarXixili.setOnClickListener {
            startActivity(Intent(this, MenuNav::class.java))
            finish()
            this.getSharedPreferences("validar5", 0).edit().putBoolean("validar5", true).apply()
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
            binding.imgTexto1original.getAlpha().toInt() == 255
            && binding.imgTexto2original.getAlpha().toInt() == 255
            && binding.imgTexto3original.getAlpha().toInt() == 255
            && binding.imgTexto4original.getAlpha().toInt() == 255) {
            binding.gifAplausosXixili.visibility = ImageView.VISIBLE

            mostrarGif()

            estadoAudio = "play"
            iniciarServicioAudio(estadoAudio, audioSeleccionado)

            // Elementos a ocultar
            binding.btnComprobarXixili.visibility = Button.GONE
            binding.btnVolverExplicacionXixili.visibility = Button.GONE

            // Elementos a mostrar
            binding.btnFinalizarXixili.visibility = Button.VISIBLE

        } else {
            // Resetear el juego
            finish()
            startActivity(Intent(this, XixiliActivity::class.java)
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
        val ImageView: ImageView = binding.gifAplausosXixili
        Glide.with(this).load(R.drawable.aplausos).into(ImageView)
    }
}