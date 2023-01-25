package com.example.merakiapp.juegos

import android.R
import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.merakiapp.Dialogos
import com.example.merakiapp.Explicaciones
import com.example.merakiapp.Recursos
import com.example.merakiapp.databinding.ActivityIslaIzaroBinding
import com.example.merakiapp.servicios.ServicioAudios
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.socket.client.IO
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Thread.sleep


class IslaIzaroActivity : AppCompatActivity(), Dialogos, Explicaciones {
    private lateinit var binding: ActivityIslaIzaroBinding
    private lateinit var sala:String
    private lateinit var esatdoAudio :String
    var socket = IO.socket("https://merakiapp-servicio-multijugador.glitch.me")
    var boolean = false
    private  var usuario1:Boolean = false
    private  var usuario2:Boolean = false

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
        val id = intent.getIntExtra("id",0)
        val name = intent.getStringExtra("name")
        val imagen = intent.getStringExtra("imagen")


        binding.button.visibility = View.GONE

        binding.constraintLayout3.visibility = View.GONE
        binding.barraOponente?.isEnabled  =false
        binding.barraUsuario?.isEnabled  =false
        binding.txtUsuario.text= name

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
            }
        }



        binding.button2?.setOnClickListener {
            if(binding.txtNumber!!.text!!.isNotBlank()) {
                sala = Integer.parseInt(binding.txtNumber!!.text.toString()).toString()
                socket.emit("sala", sala.toInt(), name, binding.barraUsuario?.progress, imagen)
                binding.button2!!.visibility = View.GONE
                binding.txtNumber!!.visibility = View.GONE
                binding.txtSala2?.visibility = View.GONE
                binding.txtSala2?.visibility = View.GONE

                binding.txtBuscaarJugador?.visibility  = View.VISIBLE

                binding.cargar?.visibility = View.VISIBLE


            }else {
                    Toast.makeText(this, "Inserte un numero para la sala", Toast.LENGTH_SHORT).show()

                }

        }
        binding.button.setOnClickListener {
            binding.constraintLayout2.visibility = View.GONE
            binding.imagenOponente.visibility = View.GONE
            binding.imagenUsuario.visibility = View.GONE

            binding.constraintLayout3.visibility = View.VISIBLE
            binding.botonMoverBarco?.visibility  = View.GONE
            binding.btnSprint?.visibility  = View.GONE
            boolean = true
            socket.emit("audio", boolean)
            esatdoAudio = "play"
            iniciarServicioAudio(esatdoAudio, Recursos.audio_Juego_Izaro)
            binding.txtUser?.text = name



        }


        binding.barraUsuario?.max  =100
        binding.barraOponente?.max  =100
        binding.barraUsuario?.progress = 0
        binding.barraOponente?.progress = 0

        binding.botonMoverBarco?.setOnClickListener {
            socket.emit("incrementation", name,binding.barraUsuario?.progress,imagen)

            if( binding.barraUsuario?.progress ==100){
                Toast.makeText(this, "Has ganado", Toast.LENGTH_SHORT).show()
                binding.btnSprint?.isEnabled =false
                binding.botonMoverBarco?.isEnabled =false

            }else if(binding.barraOponente?.progress ==100){
                Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show()
                binding.btnSprint?.isEnabled =false
                binding.botonMoverBarco?.isEnabled =false
            }
        }


        val botonX2 = binding.btnSprint
        val animationView = binding.animationView

        binding.btnSprint?.setOnClickListener {
            socket.emit("incrementation2", name, binding.barraUsuario?.progress, imagen)

            //binding.btnVolverGaztelugatxe.visibility = View.GONE

            if( binding.barraUsuario?.progress ==100){
                Toast.makeText(this, "Has ganado", Toast.LENGTH_SHORT).show()
                binding.btnSprint?.isEnabled =false
                binding.botonMoverBarco?.isEnabled =false

            }else if(binding.barraOponente?.progress ==100){
                Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show()
                binding.btnSprint?.isEnabled =false
                binding.botonMoverBarco?.isEnabled =false
            }else {

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


        // TEMPORAL
        // ??
        this.getSharedPreferences("validar6", 0).edit().putBoolean("validar6", true).apply()
    }
    override fun onBackPressed() {
        socket.disconnect()
        super.onBackPressed()

    }

    private fun iniciarServicioAudio(estadoAudio:String, audioSeleccionado:Int){
        var intent = Intent(this,ServicioAudios::class.java)
        intent.putExtra("estadoAudio",estadoAudio)
        intent.putExtra("audioSeleccionado",audioSeleccionado)
        startService(intent)
        sleep(4100)

            var dialog = AlertDialog.Builder(this).setMessage("Solo pulsar cuando el audio termine")
                .setTitle("Ha terminado el Audio")
                // Botón "aceptar"
                .setPositiveButton("Aceptar", DialogInterface.OnClickListener
                { dialog, id ->
                    if(usuario1 == true && usuario2 == true){
                        binding.botonMoverBarco?.visibility  = View.VISIBLE

                        binding.btnSprint?.visibility = View.VISIBLE

                    }else{
                        Toast.makeText(this, "Espera al otro jugador", Toast.LENGTH_SHORT).show()
                    }
                })
                // Obliga a elegir uno de los botones para cerrar el cuadro de diálogo
                //.setCancelable (false)
                .create()
                .show()
    }

    /*

    fun prueba () {
        val jsonArrayString = "[{\"codigo\":\"OUkN-E570VF6qaqwAAAD\",\"nombre\":\"pepe\",\"punto\":0,\"imagen\":\"file:///data/user/0/com.example.merakiapp/files/IMG_0\",\"salaSeleccionada\":111}]"
        val jsonArrayString2 = "[{\"nombre\": \"aaaa\"}, {\"nombre\": \"bbbb\"}]"

        val jsonArray = JSONArray (jsonArrayString)

        (0 until jsonArray.length()).forEach {
            val jsonObject = jsonArray.getJSONObject(it)
            println ("Debug: MainActivity::prueba() " + jsonObject)
        }
    }*/
}