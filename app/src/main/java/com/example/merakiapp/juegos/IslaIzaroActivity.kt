package com.example.merakiapp.juegos

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.merakiapp.Dialogos
import com.example.merakiapp.databinding.ActivityIslaIzaroBinding

class IslaIzaroActivity : AppCompatActivity(), Dialogos {
    private lateinit var binding: ActivityIslaIzaroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        //Deshabilitar menu superior
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityIslaIzaroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(this.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false){
           // binding.btnVolverGaztelugatxe.visibility = View.VISIBLE
        }else{
            //binding.btnVolverGaztelugatxe.visibility = View.GONE
        }
        // TEMPORAL
        this.getSharedPreferences("validar6", 0).edit().putBoolean("validar6", true).apply()
    }
}