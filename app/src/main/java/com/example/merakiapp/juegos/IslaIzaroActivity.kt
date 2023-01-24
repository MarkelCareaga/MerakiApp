package com.example.merakiapp.juegos

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import com.example.merakiapp.Dialogos
import com.example.merakiapp.Explicaciones
import com.example.merakiapp.databinding.ActivityIslaIzaroBinding
import com.example.merakiapp.sqLite.Usuario
import io.socket.client.IO
import org.json.JSONArray

class IslaIzaroActivity : AppCompatActivity(), Dialogos, Explicaciones {
    private lateinit var binding: ActivityIslaIzaroBinding
    private lateinit var sala:String
    var socket = IO.socket("https://merakiapp-servicio-multijugador.glitch.me")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        //Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityIslaIzaroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        socket.connect()

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
             val usuarios = ArrayList<Usuario>()

            if (usuarioString != null) {
                val jsonUsuarios = JSONArray(usuarioString)
                (0 until jsonUsuarios.length()).forEach{

                        val jsonUsuario = jsonUsuarios.getJSONObject(it)
                        val usuario = Usuario( jsonUsuario.get("id") as Int,jsonUsuario.get("nombre") as String,jsonUsuario.get("pasosUsuario") as Int,null)
                        usuarios.add(usuario)

                        binding.txtUsuario.text = usuarios[0].nombreusuario
                        binding.txtUser?.text = usuarios[0].nombreusuario
                        binding.barraUsuario?.progress  = usuarios[0].pasosUsuario





                }
            }

        }
        binding.button2?.setOnClickListener(){
            if(binding.txtNumber!!.text!!.isNotBlank()) {
                sala = Integer.parseInt(binding.txtNumber!!.getText().toString()).toString();
                socket.emit("sala", sala.toInt(), name, binding.barraUsuario?.progress, imagen)
                binding.button2!!.visibility = View.GONE
                binding.txtNumber!!.visibility = View.GONE
                binding.txtSala2?.visibility = View.GONE
                binding.txtSala2?.visibility = View.GONE

                binding.button.visibility = View.VISIBLE
                binding.imagenOponente.visibility = View.VISIBLE
                binding.txtOponente.visibility = View.VISIBLE
                binding.versus.visibility = View.VISIBLE



            }else {
                    Toast.makeText(this, "Inserte un numero para la sala", Toast.LENGTH_SHORT).show()

                }

        }
        binding.button.setOnClickListener() {
            binding.constraintLayout2.visibility = View.GONE
            binding.imagenOponente.visibility = View.GONE
            binding.imagenUsuario.visibility = View.GONE

            binding.constraintLayout3.visibility = View.VISIBLE

            binding.txtUser?.text = name


        }
        binding.barraUsuario?.max  =100
        binding.barraOponente?.max  =100
        binding.barraUsuario?.setProgress(0)
        binding.barraOponente?.setProgress(0)

        binding.botonMoverBarco?.setOnClickListener(){
            socket.emit("incrementation", name,binding.barraUsuario?.progress,imagen)
            val progreso = binding.barraUsuario?.getProgress()

            if (progreso != null) {
                binding.barraUsuario?.setProgress(progreso + 1)
            }
            if( binding.barraUsuario?.progress ==100){
                Toast.makeText(this, "Has ganado", Toast.LENGTH_SHORT).show()
            }else if(binding.barraOponente?.progress ==100){
                Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show()
            }
        }


        val botonX2 = binding.btnSprint
        val animationView = binding.animationView

        binding.btnSprint?.setOnClickListener() {
            socket.emit("incrementation2", name, binding.barraUsuario?.progress, imagen)


            //binding.btnVolverGaztelugatxe.visibility = View.GONE
            val progreso = binding.barraUsuario?.getProgress()

            if (progreso != null) {
                binding.barraUsuario?.setProgress(progreso + 2)
            }

            if( binding.barraUsuario?.progress ==100){
                Toast.makeText(this, "Has ganado", Toast.LENGTH_SHORT).show()
            }else if(binding.barraOponente?.progress ==100){
                    Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show()

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
}