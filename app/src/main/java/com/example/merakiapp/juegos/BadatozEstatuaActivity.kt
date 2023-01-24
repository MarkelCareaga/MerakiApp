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
import com.example.merakiapp.Dialogos.Companion.mensajeBadatoz
import com.example.merakiapp.Dialogos.Companion.tituloJuegos
import com.example.merakiapp.databinding.ActivityBadatozEstatuaBinding
import com.example.merakiapp.Explicaciones
import com.example.merakiapp.servicios.ServicioAudios

class BadatozEstatuaActivity : AppCompatActivity(), Dialogos, Explicaciones {
    private lateinit var binding: ActivityBadatozEstatuaBinding
    private lateinit var Imagen : ImageView
    var estadoAudio = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        // Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityBadatozEstatuaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Comprobar si el juego ha sido reiniciado.
        // En dicho caso, mostrará un aviso sobre que el resultado del juego es incorrecto.
        var resultadoJuego = intent.getStringExtra("resultadoJuego").toString()
        if(resultadoJuego == "mal") {
            mostrar_fallo_juego(this)
        }


        // ---------------------- BOTONES AYUDA Y ROTACIÓN ----------------------
        // AYUDA
        binding.btnAyudaBadatoz.setOnClickListener {
            val mensaje = mensajeBadatoz
            mostrar_dialog(this, tituloJuegos, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaBadatoz.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }


        // ---------------------- AUDIO AL INICIAR EL JUEGO --------------------------
        // Reproducir audio
        estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, Recursos.audio_Juego_Badatoz)
        var intent = Intent(this, ServicioAudios::class.java)


        // ---------------------------------------------------------------------------
        // DRAG -> Imágenes a mover
        binding.imagen1drag.setOnLongClickListener(longClickListener)
        binding.imagen2drag.setOnLongClickListener(longClickListener)
        binding.imagen3drag.setOnLongClickListener(longClickListener)
        binding.imagen4drag.setOnLongClickListener(longClickListener)
        binding.imagen5drag.setOnLongClickListener(longClickListener)
        binding.imagen6drag.setOnLongClickListener(longClickListener)
        binding.imagen7drag.setOnLongClickListener(longClickListener)
        binding.imagen8drag.setOnLongClickListener(longClickListener)
        binding.imagen9drag.setOnLongClickListener(longClickListener)

        // TARGET -> Destino de las imágenes
        binding.imagen1target.setOnDragListener(dragListener)
        binding.imagen2target.setOnDragListener(dragListener)
        binding.imagen3target.setOnDragListener(dragListener)
        binding.imagen4target.setOnDragListener(dragListener)
        binding.imagen5target.setOnDragListener(dragListener)
        binding.imagen6target.setOnDragListener(dragListener)
        binding.imagen7target.setOnDragListener(dragListener)
        binding.imagen8target.setOnDragListener(dragListener)
        binding.imagen9target.setOnDragListener(dragListener)


        // ---------------------- POR DEFECTO ----------------------
        // Por defecto, las imágenes del DRAG no se ven en el TARGET
        binding.imagen1target.alpha = 0.toFloat()
        binding.imagen2target.alpha = 0.toFloat()
        binding.imagen3target.alpha = 0.toFloat()
        binding.imagen4target.alpha = 0.toFloat()
        binding.imagen5target.alpha = 0.toFloat()
        binding.imagen6target.alpha = 0.toFloat()
        binding.imagen7target.alpha = 0.toFloat()
        binding.imagen8target.alpha = 0.toFloat()
        binding.imagen9target.alpha = 0.toFloat()

        // El GIF de los aplausos está oculto
        binding.gifAplausosBadatoz.visibility = ImageView.INVISIBLE

        // El botón Finalizar esta oculto
        binding.btnFinalizarBadatoz.visibility = Button.GONE


        // ---------------------- CONTROL DE BOTONES ----------------------
        // COMPROBAR RESULTADO
        binding.btnComprobarBadatoz.setOnClickListener {
            comprobarpuzzle()
        }

        // VOLVER A LA EXPLICACIÓN
        binding.btnVolverExplicacionBadatoz.setOnClickListener {
            stopService(intent)
            finish()

            intent = abrirExplicacion(this, Recursos.pantalla_Badatoz,
                Recursos.audio_Badatoz, Recursos.fondo_Badatoz)
            startActivity(intent)
        }

        // FINALIZAR JUEGO
        binding.btnFinalizarBadatoz.setOnClickListener {
            stopService(intent)
            finish()

            startActivity(Intent(this, MenuNav::class.java))

            // ???
            this.getSharedPreferences("validar2", 0).edit().putBoolean("validar2", true).apply()
        }
    }

    // Generar imágen para arrastrar
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

    // LongClickListener -> Mantener pulsado sobre una imagen
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
    // Declaración del listener para detectar eventos de arrastre (drag)
    private val dragListener = View.OnDragListener { v, event ->
        // Se convierte la vista en un objeto ImageView
        val receiverView: ImageView = v as ImageView

        // Se evalúa la acción detectada en el evento de arrastre
        when (event.action) {
            // Acción detectada: Inicio del evento de arrastre
            DragEvent.ACTION_DRAG_STARTED -> {
                // Se hace visible la imagen
                Imagen.visibility = View.VISIBLE
                true
            }

            // Acción detectada: Objeto arrastrado entra a la vista
            DragEvent.ACTION_DRAG_ENTERED -> {
                // Si la etiqueta (target) del objeto arrastrado es igual a la etiqueta (target) de la vista receptora
                if (event.clipDescription.label == receiverView.tag as String) {
                    // Se oculta la imagen
                    Imagen.visibility = View.GONE
                } else {
                    // Se hace visible la imagen
                    Imagen.visibility = View.VISIBLE
                }
                // Se invalida la vista
                v.invalidate()
                true
            }

            // Acción detectada: Objeto arrastrado se mueve dentro de la vista
            DragEvent.ACTION_DRAG_LOCATION -> {
                // Se hace visible la imagen
                Imagen.visibility = View.VISIBLE

                true
            }

            // Acción detectada: Objeto arrastrado sale de la vista
            DragEvent.ACTION_DRAG_EXITED -> {
                // Si la etiqueta (target) del objeto arrastrado es igual a la etiqueta (target) de la vista receptora
                if (event.clipDescription.label == receiverView.tag as String) {
                    // Se hace visible la imagen
                    Imagen.visibility = View.VISIBLE
                    // Se invalida la vista
                    v.invalidate()
                }
                true
            }

            DragEvent.ACTION_DROP -> {
                // Si la etiqueta (target) del objeto arrastrado es igual a la etiqueta (target) de la vista receptora
                if (event.clipDescription.label == receiverView.tag as String) {
                    // Se establece la opacidad de la vista receptora en 255 (totalmente visible)
                    receiverView.alpha = 255.toFloat()
                    // Se oculta la imagen
                    Imagen.visibility = View.GONE
                } else {
                    // Se hace visible la imagen
                    Imagen.visibility = View.VISIBLE
                }
                true
            }

            // Acción detectada: Fin del evento de arrastre
            DragEvent.ACTION_DRAG_ENDED -> {
                true
            } else -> {
                false
            }
        }
    }


    // ---------------------- FUNCIONES ADICIONALES ----------------------
    // Función para comprobar el resultado del juego
    private fun comprobarpuzzle(){
        // Resultado CORRECTO
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

           // Mostrar Gif de aplausos
           mostrarGif()

           // Reproducir el audio de Gritos
           estadoAudio = "play"
           iniciarServicioAudio(estadoAudio, Recursos.audio_Gritos)

           // Elementos a ocultar
           binding.btnComprobarBadatoz.visibility = Button.GONE
           binding.btnVolverExplicacionBadatoz.visibility = Button.GONE

           // Elementos a mostrar
           binding.btnFinalizarBadatoz.visibility = Button.VISIBLE

       } else {
           // Resultado INCORRECTO
           // Resetear la Activity
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

    // Función para mostrar el Gif de Aplausos
    private fun mostrarGif() {
        val ImageView: ImageView = binding.gifAplausosBadatoz
        Glide.with(this).load(R.drawable.aplausos).into(ImageView)
    }

    // Función que controla el botón Back del dispositivo móvil
    override fun onBackPressed() {
        // Detiene el audio que se está reproduciendo
        var intent = Intent(this, ServicioAudios::class.java)
        stopService(intent)

        // Abre la activity de Explicación
        finish()
        intent = abrirExplicacion(this, Recursos.pantalla_Badatoz,
            Recursos.audio_Badatoz, Recursos.fondo_Badatoz)
        startActivity(intent)
    }
}