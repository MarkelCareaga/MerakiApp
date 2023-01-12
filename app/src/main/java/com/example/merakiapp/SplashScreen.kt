package com.example.merakiapp

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Timer
import java.util.TimerTask

class SplashScreen : AppCompatActivity() {

    // Tiempo en milisegundos que se mostrará la pantalla de bienvenida
    private val SplashTime:Long = 2000 // 3 segundos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_splash_screen)

        //Ocultar toolbar
        supportActionBar?.hide()

        // Obtiene una referencia al ProgressBar de la vista
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        // Crea un ObjectAnimator para animar el progreso del ProgressBar
        val progressAnimator = ObjectAnimator.ofInt(progressBar,"progress",0,100)
        progressAnimator.duration = 2000
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.start()

        var progreso= findViewById<TextView>(R.id.txtPorcentaje)
        Thread(Runnable {
            for (i in 0 .. 100) {
                Thread.sleep(20)
                runOnUiThread {progreso.text="$i%" }
            }
        }).start()


        val intent=Intent(this,Inicio::class.java)

        // Crea un temporizador para cambiar a la siguiente actividad después del tiempo de pantalla de bienvenida
        Timer().schedule(object : TimerTask(){
            override fun run() {
                startActivity(intent)
                finish()
            }
        },SplashTime)
    }
}