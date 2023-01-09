package com.example.merakiapp.servicios

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.merakiapp.R

// Servicio encargado de gestionar las acciones realizadas sobre un audio
class ServicioAudios : Service() {

    private lateinit var mp: MediaPlayer    // Media Player
    private var length = 0                  // Posición inicial del audio
    private var pausePulsado = false        // Variable para comprobar si se ha pulsado el boton PAUSE
    private var audioSeleccionado = 0       // Variable para hacer referencia al audio seleccionado

    // Al ejecutar "StartService" desde el Activity, se ejecutará lo siguiente:
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Recoger los valores enviados desde el Activity:
        // Estado del audio (PLAY, PAUSE, ect.)
        val estadoAudio = intent?.getStringExtra("estadoAudio").toString()

        // Audio a utilizar desde el Activity
        audioSeleccionado = intent!!.getIntExtra("audioSeleccionado", 0)

        // Acciones a ejecutar, dependiendo del estado recogido.
        if (estadoAudio == "pause") {
            // PAUSE

            // Se ha pulsado el botón PAUSE
            pausePulsado = true

            // Comprobar si se ha inicializado el Media Player
            if (this::mp.isInitialized) {
                // Pausar el audio
                mp.pause()

                // Recoger la posición actual del audio
                length = mp.currentPosition
            }

        } else if (estadoAudio == "restart") {
            // RESTART

            // Comprobar si se ha inicializado el Media Player
            if (this::mp.isInitialized) {

                // Comprobar si existe un audio pausado con anterioridad
                if (mp.getCurrentPosition() > 0) {

                    // Detener el audio
                    mp.stop()
                }
            }

            iniciarAudio()

        } else if (estadoAudio == "play") {

            // Comprobar si se ha pulsado el botón PAUSE
            if (pausePulsado) {

                // RESUME
                // Comprobar si se ha inicializado el Media Player
                if (this::mp.isInitialized) {
                    // Retomar el audio desde su última posición
                    mp.seekTo(length)
                    mp.start()
                }

                // Se ha pulsado el botón PAUSE
                pausePulsado = false

            } else {
                // PLAY
                iniciarAudio()
            }
        }

        // Se reinicia el Servicio
        return START_NOT_STICKY
    }

    // Función encargada de reproducir el audio seleccionado
    fun iniciarAudio() {
        mp = MediaPlayer.create(applicationContext, audioSeleccionado)
        mp.start()
    }

    // Al ejecutar "StopService" desde el Activity, se ejecutará lo siguiente:
    override fun onDestroy() {
        super.onDestroy()

        // Comprobar si se ha inicializado el Media Player
        if (this::mp.isInitialized) {
            // Detener el audio
            mp.stop()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}