package com.example.merakiapp.juegos

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isInvisible
import com.example.merakiapp.Dialogos
import com.example.merakiapp.Explicaciones
import com.example.merakiapp.databinding.ActivityIslaIzaroBinding
import io.socket.client.IO
import org.json.JSONArray
import org.json.JSONObject

class IslaIzaroActivity : AppCompatActivity(), Dialogos, Explicaciones {
    private lateinit var binding: ActivityIslaIzaroBinding
    private lateinit var sala:String
    var socket = IO.socket("https://merakiapp-servicio-multijugador.glitch.me")

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        //Deshabilitar menu superior
        supportActionBar?.hide()
        socket.connect()

        super.onCreate(savedInstanceState)
        binding = ActivityIslaIzaroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.constraintLayout3.isInvisible=true
        binding.imageView7.isInvisible=true
        binding.txtOponente.isInvisible=true
        val id = intent.getIntExtra("id",0)
        val name = intent.getStringExtra("name")
        val imagen = intent.getStringExtra("imagen").toString()


        binding.button.visibility = View.GONE

        binding.constraintLayout3.visibility = View.GONE
        binding.barraOponente?.isEnabled  =false
        binding.barraUsuario?.isEnabled  =false
        binding.txtUsuario.text= name
        binding.imagenUsuario.setImageURI(imagen.toUri())
        socket.on("actualizar"){args->
                var data = args[0] as JSONArray
                for(pos in 0 until 2) {
                    val valor = data[pos] as JSONObject
                    if(valor["codigo"]==socket.id()){
                        binding.txtUsuario.text = ""+valor["name"]
                        binding.barraUsuario?.setProgress(valor["punt"] as Int)
                    }else{
                        binding.txtOponente.text = ""+valor["name"]
                        binding.barraUsuario?.setProgress(valor["punt"] as Int)
                        //binding.imagenOponente.setImageURI()

                    }
                }
        }
        binding.button2?.setOnClickListener(){
            if(binding.txtNumber!!.text!!.isNotBlank()) {
                sala = Integer.parseInt(binding.txtNumber!!.getText().toString()).toString();
                socket.emit("sala", sala.toInt(), name, binding.barraUsuario?.progress, imagen)
                binding.button2!!.visibility = View.GONE
                binding.txtNumber!!.visibility = View.GONE
                binding.numSala?.visibility = View.GONE
                binding.button.visibility = View.VISIBLE



            }else {
                    Toast.makeText(this, "Inserte un numero para la sala", Toast.LENGTH_SHORT).show()

                }

        }
        binding.button.setOnClickListener() {


            binding.constraintLayout3.visibility = View.VISIBLE
            binding.constraintLayout2.visibility = View.GONE

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
        // TEMPORAL
        this.getSharedPreferences("validar6", 0).edit().putBoolean("validar6", true).apply()
    }
}