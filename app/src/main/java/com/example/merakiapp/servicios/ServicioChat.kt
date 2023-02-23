package com.example.merakiapp.servicios

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.socket.client.IO
import org.json.JSONArray

class ServicioChat : Service() {

    var socketChat = IO.socket("https://merakiapp-chatglobal.glitch.me")
    lateinit var socketId: String
    lateinit var jsonarray: JSONArray

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        socketChat.connect()
        socketId = socketChat.id()
        return super.onStartCommand(intent, flags, startId)
    }

    fun sala(nombreUsuario: String, sala: String) {
        socketChat.emit("sala", sala, nombreUsuario)
    }

    fun enviarmensaje(nombreUsuario: String, sala: String, mensaje: String, fecha: String) {
        socketChat.emit("EnviarMensaje", socketId, nombreUsuario, sala, mensaje, fecha)
    }

    fun recuperarchat(): JSONArray {
        socketChat.on("actualizarJuego") { usuarioString ->
            // Pasamos a un JSONarray todos los usuarios
            jsonarray = usuarioString[0] as JSONArray

        }
        return jsonarray
    }

    override fun onDestroy() {
        socketChat.disconnect()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

}