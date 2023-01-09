package com.example.merakiapp.juegos

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.merakiapp.databinding.ActivityPuertaSanJuanBinding
import com.example.merakiapp.databinding.ActivitySopaLetrasBinding

class SopaLetrasActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySopaLetrasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        super.onCreate(savedInstanceState)
        binding = ActivitySopaLetrasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(this.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false){
            //binding.btnVolverPuertaSanJuan.visibility = View.VISIBLE
        }else{
           // binding.btnVolverPuertaSanJuan.visibility = View.GONE
        }

    }
}