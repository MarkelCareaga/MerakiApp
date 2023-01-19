package com.example.merakiapp.juegos

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
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

        super.onCreate(savedInstanceState)
        binding = ActivityIslaIzaroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id",0)
        val name = intent.getStringExtra("name")
        val imagen = intent.getStringExtra("imagen").toString()
        socket.connect()
        socket.emit("sala" , sala.toInt(),name,binding.barraUsuario?.progress ,imagen)




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

        binding.button.setOnClickListener(){
            binding.constraintLayout3.visibility = View.VISIBLE
            binding.constraintLayout2.visibility = View.GONE

            binding.txtUser?.text =  name


        }
        binding.barraUsuario?.max  =100
        binding.barraOponente?.max  =100
        binding.barraUsuario?.setProgress(0)
        binding.barraOponente?.setProgress(0)

        binding.botonMoverBarco?.setOnClickListener(){
            socket.emit("incrementation", name,binding.barraUsuario?.progress,imagen)
        }
        binding.btnSprint?.setOnClickListener(){
            socket.emit("incrementation2", name,binding.barraUsuario?.progress,imagen)
        }
        if(this.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false){
           // binding.btnVolverGaztelugatxe.visibility = View.VISIBLE
        }else{
            //binding.btnVolverGaztelugatxe.visibility = View.GONE
        }
        // TEMPORAL
        this.getSharedPreferences("validar6", 0).edit().putBoolean("validar6", true).apply()
    }
}