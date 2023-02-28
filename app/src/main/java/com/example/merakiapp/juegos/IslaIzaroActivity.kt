package com.example.merakiapp.juegos

//noinspection SuspiciousImport
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.merakiapp.explicaciones.Explicaciones
import com.example.merakiapp.listas.ListaDialogos
import com.example.merakiapp.listas.ListaRecursos
import com.example.merakiapp.MenuNav
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ActivityIslaIzaroBinding
import com.example.merakiapp.servicios.ServicioAudios
import com.example.merakiapp.sqLite.SeleccionarUsuario
import io.socket.client.IO
import org.json.JSONArray
import org.json.JSONObject

class IslaIzaroActivity : AppCompatActivity(), Explicaciones {
    private lateinit var binding: ActivityIslaIzaroBinding
    private lateinit var sala: String
    private lateinit var estadoAudio: String
    var socket = IO.socket("https://merakiapp-servicio-multijugador.glitch.me")
    var boolean = false
    private var usuario1: Boolean = false
    private var usuario2: Boolean = false
    private var listaDialogos = ListaDialogos()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityIslaIzaroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Conectamos con el servidor de socket io
        socket.connect()

        // Definimos que no se ven al princpio de la actividad
        binding.txtBuscaarJugador?.visibility = View.GONE
        binding.cargar?.visibility = View.GONE
        binding.constraintLayout3.visibility = View.GONE
        binding.versus.visibility = View.GONE
        binding.txtOponente.visibility = View.GONE
        binding.imagenOponente.visibility = View.GONE
        binding.btnFinalizarCarrera?.visibility = View.GONE
        binding.gifAplausosCarrera?.visibility = View.GONE
        binding.btnJugar.visibility = View.GONE
        binding.constraintLayout3.visibility = View.GONE

        // Los seekbars no se puden desplazar con los dedos 
        binding.barraOponente?.isEnabled = false
        binding.barraUsuario?.isEnabled = false

        // Cogemos los valores de los usuarios que necesitamos
        val name = intent.getStringExtra("name")
        val imagen = intent.getStringExtra("imagen")

        // Le damos txtusuario el nombre del usuario elegido
        binding.txtUsuario.text = name


        // AUDIO
        // Conexión con el Servicio de Audios
        val intent = Intent(this, ServicioAudios::class.java)
        // Comprobamos que la imagen del usuaria se nula
        if (imagen != null) {
            // Si no es nula, le insertamos la imagen en un Imageview
            binding.imagenUsuario.setImageURI(imagen.toUri())
        }


        // Llamamos al metodo del socket para actualizar los valores de los usuarios
        socket.on("actualizarJuego") { usuarioString ->
            // Hacemos un runOnUiThread para que no se salte niguns salto y carge bien los datos
            runOnUiThread {
                // Pasamos a un JSONarray todos los usuarios
                val jsonarray = usuarioString[0] as JSONArray
                // Nos recorremos el JSONarray
                (0 until jsonarray.length()).forEach {
                    // Creamos la variable objeto que recivira la fila de cada usuario
                    val objeto: JSONObject = jsonarray.getJSONObject(it)
                    // Comprobamos si uno de los usuario tiene la misma id que la nuestra
                    if (objeto["idcode"] == socket.id()) {
                        // Si tiene la misma id, es el jugador 1 (propietario del dispositivo)
                        // Le damos los valores del nombre al usuario con los datos que recibimpos del socket
                        binding.txtUsuario.text = "" + objeto["nombre"]
                        binding.txtUser?.text = "" + objeto["nombre"]
                        // Le damos los valores de los puntos al usuario con los datos que recibimpos del socket
                        binding.barraUsuario?.progress = objeto.getInt("punto")
                        // Le damos el valor del audio que recogemos del socket
                        usuario1 = objeto.getBoolean("audio")

                    } else {
                        // Si es el oponente...
                        if (!jsonarray.getJSONObject(1).isNull("nombre")) {
                            // Escondemos el txtBuscaarJugador y el progresBar
                            binding.txtBuscaarJugador?.visibility = View.GONE
                            binding.cargar?.visibility = View.GONE

                            // Hacemos visible las siguientes cosas
                            binding.imagenOponente.visibility = View.VISIBLE
                            binding.txtOponente.visibility = View.VISIBLE
                            binding.versus.visibility = View.VISIBLE
                            binding.btnJugar.visibility = View.VISIBLE

                            // Le damos los valores del nombre al usuario con los datos que recibimpos del socket
                            binding.txtOponente.text = "" + objeto["nombre"]
                            binding.txtUserOponent?.text = "" + objeto["nombre"]
                            // Le damos los valores de los puntos al usuario con los datos que recibimpos del socket
                            binding.barraOponente?.progress = objeto.getInt("punto")
                            // Le damos el valor del audio que recogemos del socket
                            usuario2 = objeto.getBoolean("audio")

                        }
                    }

                }

                // Comprobamos si los dos usuario han escuchado el audio
                if (usuario1 && usuario2) {
                    // Si lo han escuchado...
                    // Hacemos visble los botones para la carrera
                    binding.botonMoverBarco?.visibility = View.VISIBLE
                    binding.btnSprint?.visibility = View.VISIBLE

                }
            }
        }


//------------------------------------ BOTÓN BUSCAR ----------------------------------------------//
        binding.btnBuscar?.setOnClickListener {
            // Comprobar si a insertado un número
            if (binding.txtNumber!!.text!!.isNotBlank()) {
                // Si a insertado un número..
                // Recogemos en sala el valor del numero
                sala = Integer.parseInt(binding.txtNumber!!.text.toString()).toString()
                // Llamamos al spcket para que le conecte a esa sala y le pasamos unos argumentos
                socket.emit("sala", sala.toInt(), name, binding.barraUsuario?.progress, imagen)
                // Se ocultan las siguientes cosas
                binding.btnBuscar!!.visibility = View.GONE
                binding.txtNumber!!.visibility = View.GONE
                binding.txtSala2?.visibility = View.GONE
                binding.txtSala2?.visibility = View.GONE
                // Se hacen visibles las siguientes cosas
                binding.txtBuscaarJugador?.visibility = View.VISIBLE
                binding.cargar?.visibility = View.VISIBLE
            } else {
                // Si no a insertado un número...
                Toast.makeText(this, getString(R.string.numerosala), Toast.LENGTH_SHORT).show()
            }
        }


//------------------------------------ BOTÓN JUGAR ----------------------------------------------//
        binding.btnJugar.setOnClickListener {
            // Se ocultan las siguientes cosas
            binding.constraintLayout2.visibility = View.GONE
            binding.imagenOponente.visibility = View.GONE
            binding.imagenUsuario.visibility = View.GONE
            binding.botonMoverBarco?.visibility = View.GONE
            binding.btnSprint?.visibility = View.GONE

            // Se hacen visibles las siguientes cosas
            binding.constraintLayout3.visibility = View.VISIBLE

            // Estado y servicio del audio
            estadoAudio = "play"
            iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Juego_Izaro, true)
        }

        // Le damos valores a los seekbars
        binding.barraUsuario?.max = 100
        binding.barraOponente?.max = 100
        binding.barraUsuario?.progress = 0
        binding.barraOponente?.progress = 0


//------------------------------------ BOTÓN MOVER BARCO ----------------------------------------//
        binding.botonMoverBarco?.setOnClickListener {
            // Llamamos al socket io a la funcion incrementation y le pasamos unos argumentos
            socket.emit("incrementation", name, binding.barraUsuario?.progress, imagen)
            // Comprobar si alguien ya ha ganado
            if (binding.barraUsuario?.progress == 100) {
                // Si ha gando el usuario 1
                partidaGanada()
            } else if (binding.barraOponente?.progress == 100) {
                // Si ha pierde el usuario 1
                partidaPerdida()
            }
        }

        val botonX2 = binding.btnSprint
        val animationView = binding.animationView


//------------------------------------ BOTÓN SPRINT ----------------------------------------------//
        binding.btnSprint?.setOnClickListener {
            // Llamamos al socket io a la funcion incrementation y le pasamos unos argumentos
            socket.emit("incrementation2", name, binding.barraUsuario?.progress, imagen)

            //binding.btnVolverGaztelugatxe.visibility = View.GONE
            // Comprobar si alguien ya ha ganado
            if (binding.barraUsuario?.progress == 100) {
                // Si ha gando el usuario 1
                partidaGanada()
            } else if (binding.barraOponente?.progress == 100) {
                // Si ha pierde el usuario 1
                partidaPerdida()
            } else {
                // Si no se comple ninguna
                // El botón se queda deshabilitado durante 5s
                botonX2!!.isEnabled = false
                animationView!!.speed = 0.3f
                animationView.playAnimation()
                Thread {
                    Thread.sleep(5000)
                    runOnUiThread {
                        botonX2.isEnabled = true
                        animationView.cancelAnimation()
                    }
                }.start()
            }
        }

        // FINALIZAR
        binding.btnFinalizarCarrera?.setOnClickListener {
            // se para el audio y se finaliza el activity
            stopService(intent)
            finish()
            // se le lleva al mapa
            startActivity(Intent(this, MenuNav::class.java))
        }
    }


    // ------------------------------- NEW -------------------------------
    fun partidaGanada() {
        // Se desconecta del socket io
        socket.disconnect()
        // Muestra mensaje de victoria
        Toast.makeText(this, getString(R.string.ganar), Toast.LENGTH_SHORT).show()
        // Deshabilitar los botones sprint y moverBarco
        binding.btnSprint?.isEnabled = false
        binding.botonMoverBarco?.isEnabled = false
        // Ocultar la animacion
        binding.animationView?.visibility = View.GONE
        // Hacer visibles los siguientes botones
        binding.btnFinalizarCarrera?.visibility = ImageButton.VISIBLE
        binding.gifAplausosCarrera?.visibility = View.VISIBLE

        // Mostrar el GIF de los aplausos
        mostrarGif()

        // Reproducir audio
        val estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Gritos, false)
        // Dar el valor true a validar6
        this.getSharedPreferences("validar6", 0).edit().putBoolean("validar6", true).apply()
    }

    fun partidaPerdida() {
        // Se desconecta del socket io
        socket.disconnect()
        // Muestra mensaje de derrota
        Toast.makeText(this, getString(R.string.perder), Toast.LENGTH_SHORT).show()
        // Deshabilitar los botones sprint y moverBarco
        binding.btnSprint?.isEnabled = false
        binding.botonMoverBarco?.isEnabled = false
        // Ocultar la animacion
        binding.animationView?.visibility = View.GONE
        // Hacer visible el siguiente botones
        binding.btnFinalizarCarrera?.visibility = ImageButton.VISIBLE

        // Dar el valor true a validar6
        this.getSharedPreferences("validar6", 0).edit().putBoolean("validar6", true).apply()
    }

    // Función para mostrar el GIF de los aplausos
    private fun mostrarGif() {
        val imageView: ImageView? = binding.gifAplausosCarrera
        if (imageView != null) {
            Glide.with(this).load(com.example.merakiapp.R.drawable.aplausos).into(imageView)
        }
    }
    // -------------------------------------------------------------------

    // Volver atrás
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        socket.disconnect()
        // Detiene el audio que se está reproduciendo
        var intent = Intent(this, ServicioAudios::class.java)
        stopService(intent)

        intent = Intent(this, SeleccionarUsuario::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    // Iniciar audio del juego
    private fun iniciarServicioAudio(
        estadoAudio: String,
        audioSeleccionado: Int,
        conDialog: Boolean
    ) {
        // Llamar al servicio del audio
        val intent = Intent(this, ServicioAudios::class.java)
        intent.putExtra("estadoAudio", estadoAudio)
        intent.putExtra("audioSeleccionado", audioSeleccionado)
        startService(intent)
        Thread(Runnable {
            // Dar 40 segundos para que se reproduzca el audio
            Thread.sleep(4100)
            runOnUiThread {
                // Lanzar un dialog cuando se acaben los segundos
                if (conDialog) {
                    AlertDialog.Builder(this).setMessage("3, 2, 1 .... YA")
                        .setTitle("¿Preparados?")
                        // Botón "aceptar"
                        .setPositiveButton("¡Vamos!", DialogInterface.OnClickListener
                        { dialog, id ->
                            boolean = true
                            socket.emit("audio", boolean)
                            if (usuario1 && usuario2) {
                                binding.botonMoverBarco?.visibility = View.VISIBLE
                                binding.btnSprint?.visibility = View.VISIBLE
                            } else {
                                Toast.makeText(
                                    this,
                                    "Espera al otro jugador",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                        // Obliga a elegir uno de los botones para cerrar el cuadro de diálogo
                        //.setCancelable (false)
                        .create()
                        .show()
                }
            }
        }).start()
    }
}