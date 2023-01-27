package com.example.merakiapp

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    // Tiempo en milisegundos que se mostrará la pantalla de bienvenida
    private val duracion_splash: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Deshabilitar rotación de pantalla (Landscape)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_splash_screen)

        // Ocultar toolbar
        supportActionBar?.hide()

        // Obtiene una referencia al ProgressBar de la vista
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // Crea un ObjectAnimator para animar el progreso del ProgressBar
        val progressAnimator = ObjectAnimator.ofInt(progressBar,"progress",0,100)

        // La barra de progeso durara 2 segundos
        progressAnimator.duration = 2000
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.start()

        val progreso= findViewById<TextView>(R.id.txtPorcentaje)
        Thread(Runnable {
            for (i in 0 .. 100) {
                Thread.sleep(duracion_splash.tiempo_hilo())
                runOnUiThread {progreso.text = "$i%" }
            }
        }).start()

        val intent = Intent(this,Inicio::class.java)

        // Crea un temporizador para cambiar a la siguiente actividad después del tiempo de pantalla de bienvenida
        Timer().schedule(object : TimerTask(){
            override fun run() {
                startActivity(intent)
                finish()
            }
        },duracion_splash.milisegundos())
    }

    // Funciones de extensión
    private fun Int.milisegundos(): Long {
        return (this * 1000).toLong()
    }

    private fun Int.tiempo_hilo(): Long {
        return (this * 10).toLong()
    }

}