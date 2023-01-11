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
    // Tiempo en milisegundos que se mostrará la pantalla de bienvenida
    private val SplashTime:Long = 5000 // 5 segundos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Obtiene una referencia al ProgressBar de la vista
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        // Crea un ObjectAnimator para animar el progreso del ProgressBar
        val progressAnimator = ObjectAnimator.ofInt(progressBar,"progress",0,100)
        progressAnimator.duration = 5000
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.start()

        val intent=Intent(this,Inicio::class.java)

        // Crea un temporizador para cambiar a la siguiente actividad después del tiempo de pantalla de bienvenida
        Timer().schedule(object : TimerTask(){
            override fun run() {
                startActivity(intent)
                finish()
            }
        },SplashTime)

        // Verifica si la aplicación tiene permisos para acceder a la ubicación del dispositivo
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
            // Si no tiene permisos, solicita permisos para ACCESS_FINE_LOCATION y ACCESS_COARSE_LOCATION
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