package com.example.merakiapp.juegos

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Canvas
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.merakiapp.*
import com.example.merakiapp.databinding.ActivityXixiliBinding
import com.example.merakiapp.explicaciones.Explicaciones
import com.example.merakiapp.listas.ListaDialogos
import com.example.merakiapp.listas.ListaRecursos
import com.example.merakiapp.servicios.ServicioAudios

class XixiliActivity : AppCompatActivity(), Explicaciones {
    private lateinit var binding: ActivityXixiliBinding
    private lateinit var imagen : ImageView
    private var estadoAudio = ""

    private var listaDialogos = ListaDialogos()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityXixiliBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Comprobar si el juego ha sido reiniciado.
        // En dicho caso, mostrará un aviso sobre que el resultado del juego es incorrecto.
        val resultadoJuego = intent.getStringExtra("resultadoJuego").toString()
        if(resultadoJuego == "mal") {
            listaDialogos.mostrar_fallo_juego(this)
        }

        // --------------------- BOTONES AYUDA Y ROTACIÓN ---------------------
        // AYUDA
        binding.btnAyudaXixili.setOnClickListener {
            val mensaje = ListaRecursos.mensajeXixili
            listaDialogos.mostrar_dialog(this, ListaRecursos.tituloJuegos, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaXixili.setOnClickListener {
            listaDialogos.mostrar_info_pantalla(this, false)
        }


        // ----------------------AUDIO AL INICIAR EL JUEGO--------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Juego_Xixili)
        var intent = Intent(this, ServicioAudios::class.java)

        // -------------------------------------------------------------------------
        // FONDO
        val activityXixili = binding.activityXixili
        activityXixili.background = resources.getDrawable(ListaRecursos.fondo_Xixili, theme)

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

        // El GIF de los aplausos está oculto
        binding.gifAplausosXixili.visibility = ImageView.INVISIBLE

        // El botón Finalizar esta oculto
        binding.btnFinalizarXixili.visibility = Button.GONE


        // -------------------------- CONTROL DE BOTONES --------------------------
        // COMPROBAR
        binding.btnComprobarXixili.setOnClickListener{
            comprobarpuzzle()
        }

        // VOLVER
        binding.btnVolverExplicacionXixili.setOnClickListener {
            stopService(intent)
            finish()

            intent = abrirExplicacion(this, ListaRecursos.pantalla_Xixili)
            startActivity(intent)
        }

        // FINALIZAR
        binding.btnFinalizarXixili.setOnClickListener {
            stopService(intent)
            finish()
            startActivity(Intent(this, MenuNav::class.java))

            // ???
            this.getSharedPreferences("validar5", 0).edit().putBoolean("validar5", true).apply()
        }
    }

    // ???
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

    // ???
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

        imagen = v as ImageView
        val myShadow = MyDragShadowBuilder(v)

        v.startDragAndDrop(dragData, myShadow,null,0)
        true
    }

    @SuppressLint("Range")
    private val dragListener = View.OnDragListener { v, event ->
        val receiverView: ImageView = v as ImageView

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                imagen.visibility = View.VISIBLE
                true
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                if (event.clipDescription.label == receiverView.tag as String) {
                    imagen.visibility = View.GONE
                } else {
                    imagen.visibility = View.VISIBLE
                }
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_LOCATION -> {
                imagen.visibility = View.VISIBLE
                true
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                if (event.clipDescription.label == receiverView.tag as String) {
                    imagen.visibility = View.VISIBLE
                    v.invalidate()
                }
                true
            }

            DragEvent.ACTION_DROP -> {

                if (event.clipDescription.label == receiverView.tag as String) {
                    receiverView.alpha = 255.toFloat()
                    imagen.visibility = View.GONE
                } else {
                    imagen.visibility = View.VISIBLE
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

    // Función para comprobar el resultado
    private fun comprobarpuzzle(){
        if (
            binding.imgTexto1original.getAlpha().toInt() == 255
            && binding.imgTexto2original.getAlpha().toInt() == 255
            && binding.imgTexto3original.getAlpha().toInt() == 255
            && binding.imgTexto4original.getAlpha().toInt() == 255) {
            binding.gifAplausosXixili.visibility = ImageView.VISIBLE

            // Mostrar Gif de aplausos
            mostrarGif()

            // Reproducir audio
            estadoAudio = "play"
            iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Gritos)

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
        val imageView: ImageView = binding.gifAplausosXixili
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
        intent = abrirExplicacion(this, ListaRecursos.pantalla_Xixili)
        startActivity(intent)
    }
}