package com.example.merakiapp

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import com.example.merakiapp.databinding.ActivityInicioBinding
import java.util.Timer
import java.util.TimerTask

class SplashScreen : AppCompatActivity() {
    private val SplashTime:Long = 3000 // 5 segundos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val progressBar = findViewById<ProgressBar>(R.id.progressBar)



        val progressAnimator = ObjectAnimator.ofInt(progressBar,"progress",0,100)
        progressAnimator.duration = 3000
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.start()

        val intent=Intent(this,Inicio::class.java)

        Timer().schedule(object : TimerTask(){
            override fun run() {
                startActivity(intent)
                finish()
            }
        },SplashTime)

        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )



        }else  if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return

            //binding.btnLibre.isEnabled=false
            //binding.btnExplorador.isEnabled=false


        }

    }
}