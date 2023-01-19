package com.example.merakiapp.juegos

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import com.example.merakiapp.Dialogos
import com.example.merakiapp.Explicaciones
import com.example.merakiapp.databinding.ActivityIslaIzaroBinding

class IslaIzaroActivity : AppCompatActivity(), Dialogos, Explicaciones {
    private lateinit var binding: ActivityIslaIzaroBinding

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
        val pasos = intent.getIntExtra("pasos",0)
        val imagen = intent.getStringExtra("imagen").toString()
        binding.constraintLayout3.visibility = View.GONE
        binding.barraOponente?.isEnabled  =false
        binding.barraUsuario?.isEnabled  =false
        binding.txtUsuario.text= name
        binding.imagenUsuario.setImageURI(imagen.toUri())

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
            val progreso = binding.barraUsuario?.getProgress()

            if (progreso != null) {
                binding.barraUsuario?.setProgress(progreso+1)
            }
        }


        val botonX2 = binding.btnSprint
        val animationView = binding.animationView

        binding.btnSprint?.setOnClickListener(){

            val progreso = binding.barraUsuario?.getProgress()

            if (progreso != null) {
                binding.barraUsuario?.setProgress(progreso+2)
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