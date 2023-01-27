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
import com.example.merakiapp.databinding.ActivityIslaIzaroBinding
import com.example.merakiapp.servicios.ServicioAudios
import io.socket.client.IO
import org.json.JSONArray
import org.json.JSONObject

class IslaIzaroActivity : AppCompatActivity(), Explicaciones {
    private lateinit var binding: ActivityIslaIzaroBinding
    private lateinit var sala: String
    private lateinit var estadoAudio :String
    var socket = IO.socket("https://merakiapp-servicio-multijugador.glitch.me")
    var boolean = false
    private var usuario1: Boolean = false
    private var usuario2: Boolean = false
    private var listaDialogos = ListaDialogos()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityIslaIzaroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //prueba()
        socket.connect()
        binding.txtBuscaarJugador?.visibility  = View.GONE
        binding.cargar?.visibility  = View.GONE
        binding.constraintLayout3.visibility = View.GONE
        binding.versus.visibility = View.GONE
        binding.txtOponente.visibility = View.GONE
        binding.imagenOponente.visibility= View.GONE
        val name = intent.getStringExtra("name")
        val imagen = intent.getStringExtra("imagen")


        // ------------------------ NEW ------------------------
        binding.btnFinalizarCarrera?.visibility = View.INVISIBLE
        binding.gifAplausosCarrera?.visibility = View.INVISIBLE
        // -----------------------------------------------------


        binding.button.visibility = View.GONE

        binding.constraintLayout3.visibility = View.GONE
        binding.barraOponente?.isEnabled = false
        binding.barraUsuario?.isEnabled = false
        binding.txtUsuario.text = name


        // AUDIO
        // Conexión con el Servicio de Audios
        val intent = Intent(this, ServicioAudios::class.java)

        if (imagen != null) {
            binding.imagenUsuario.setImageURI(imagen.toUri())
        }

        /*
        private fun setUpdateNicks (jsonUsersList: JSONArray) {
            chatUsers.clear()

            (0 until jsonUsersList.length()).forEach {
                val jsonUser = jsonUsersList.getJSONObject(it)
                chatUsers.add (ChatUser (jsonUser.get(ChatUser.ID) as String, jsonUser.get(ChatUser.NICK) as String))
            }

            println ("Debug: ChatManager::setUpdateNicks () - Total users: " + chatUsers.size)
        }
        */

        socket.on("actualizarJuego"){usuarioString->
            runOnUiThread {
                val jsonarray = usuarioString[0] as JSONArray

                println(jsonarray)

                (0 until jsonarray.length()).forEach {
                    val objeto: JSONObject = jsonarray.getJSONObject(it)
                    if (objeto["idcode"] == socket.id()) {
                        //El usuario
                        binding.txtUsuario.text = ""+objeto["nombre"]
                        binding.txtUser?.text = ""+objeto["nombre"]
                        binding.barraUsuario?.progress = objeto.getInt("punto")
                        usuario1 = objeto.getBoolean("audio")

                    } else {
                        if (!jsonarray.getJSONObject(1).isNull("nombre")){
                            // Oponente
                            binding.txtBuscaarJugador?.visibility  = View.GONE
                            binding.cargar?.visibility  = View.GONE


                            binding.imagenOponente.visibility = View.VISIBLE
                            binding.txtOponente.visibility = View.VISIBLE
                            binding.versus.visibility = View.VISIBLE
                            binding.button.visibility = View.VISIBLE
                            binding.txtOponente.text = ""+objeto["nombre"]
                            binding.txtUserOponent?.text = ""+objeto["nombre"]
                            binding.barraOponente?.progress = objeto.getInt("punto")
                            usuario2 = objeto.getBoolean("audio")

                        }
                    }

                }
                    if (usuario1 && usuario2) {

                        binding.botonMoverBarco?.visibility = View.VISIBLE

                        binding.btnSprint?.visibility = View.VISIBLE


                }
            }
        }



        binding.button2?.setOnClickListener {
            if (binding.txtNumber!!.text!!.isNotBlank()) {
                sala = Integer.parseInt(binding.txtNumber!!.text.toString()).toString()
                socket.emit("sala", sala.toInt(), name, binding.barraUsuario?.progress, imagen)
                binding.button2!!.visibility = View.GONE
                binding.txtNumber!!.visibility = View.GONE
                binding.txtSala2?.visibility = View.GONE
                binding.txtSala2?.visibility = View.GONE

                binding.txtBuscaarJugador?.visibility  = View.VISIBLE
                binding.cargar?.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Inserte un número para la sala", Toast.LENGTH_SHORT).show()
            }

        }
        binding.button.setOnClickListener {
            binding.constraintLayout2.visibility = View.GONE
            binding.imagenOponente.visibility = View.GONE
            binding.imagenUsuario.visibility = View.GONE

            binding.constraintLayout3.visibility = View.VISIBLE
            binding.botonMoverBarco?.visibility  = View.GONE
            binding.btnSprint?.visibility  = View.GONE

            estadoAudio = "play"
            iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Juego_Izaro, true)
            binding.txtUser?.text = name



        }


        binding.barraUsuario?.max  =100
        binding.barraOponente?.max  =100
        binding.barraUsuario?.progress = 0
        binding.barraOponente?.progress = 0

        binding.botonMoverBarco?.setOnClickListener {
            socket.emit("incrementation", name,binding.barraUsuario?.progress,imagen)

            if (binding.barraUsuario?.progress == 100) {
                partidaGanada()
            } else if (binding.barraOponente?.progress == 100) {
                partidaPerdida()
            }
        }


        val botonX2 = binding.btnSprint
        val animationView = binding.animationView

        binding.btnSprint?.setOnClickListener {
            socket.emit("incrementation2", name, binding.barraUsuario?.progress, imagen)

            //binding.btnVolverGaztelugatxe.visibility = View.GONE

            if (binding.barraUsuario?.progress == 100) {
                partidaGanada()
            } else if (binding.barraOponente?.progress == 100) {
                partidaPerdida()
            } else {
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
            socket.disconnect()

            stopService(intent)
            finish()

            startActivity(Intent(this, MenuNav::class.java))
        }
    }


    // ------------------------------- NEW -------------------------------
    fun partidaGanada() {
        Toast.makeText(this, "Has ganado", Toast.LENGTH_SHORT).show()
        binding.btnSprint?.isEnabled = false
        binding.botonMoverBarco?.isEnabled = false
        binding.animationView?.visibility  = View.GONE
        binding.btnFinalizarCarrera?.visibility = ImageButton.VISIBLE
        binding.gifAplausosCarrera?.visibility = View.VISIBLE

        // Mostrar el GIF de los aplausos
        mostrarGif()

        // Reproducir audio
        val estadoAudio = "play"
        iniciarServicioAudio(estadoAudio, ListaRecursos.audio_Gritos, false)

        this.getSharedPreferences("validar6", 0).edit().putBoolean("validar6", true).apply()
    }

    fun partidaPerdida() {
        Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show()
        binding.btnSprint?.isEnabled = false
        binding.botonMoverBarco?.isEnabled = false
        binding.animationView?.visibility  = View.GONE
        binding.btnFinalizarCarrera?.visibility = ImageButton.VISIBLE

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


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        socket.disconnect()
    }

    private fun iniciarServicioAudio(estadoAudio:String, audioSeleccionado:Int, conDialog: Boolean) {
        val intent = Intent(this,ServicioAudios::class.java)
        intent.putExtra("estadoAudio",estadoAudio)
        intent.putExtra("audioSeleccionado",audioSeleccionado)
        startService(intent)
        Thread(Runnable {
            Thread.sleep(4100)
            runOnUiThread {
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
                                Toast.makeText(this, "Espera al otro jugador", Toast.LENGTH_SHORT).show()
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