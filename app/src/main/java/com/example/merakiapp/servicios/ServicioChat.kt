package com.example.merakiapp.servicios

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.socket.client.IO
import org.json.JSONArray
import org.json.JSONObject

class ServicioChat : Service() {

    val socketChat = IO.socket("https://merakiapp-chatglobal.glitch.me")
    var socketId: String = ""
    private var jsonarray: JSONArray? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        socketChat.connect()
        if (socketChat.connected()){
            socketId = socketChat.id()
        }
        else {
            println("ERROR: Socket")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun sala(nombreUsuario: String, sala: String) {
        socketChat.emit("sala", sala, nombreUsuario)
    }

    fun enviarmensaje(nombreUsuario: String, sala: String, mensaje: String, fecha: String) {
        socketChat.emit("EnviarMensaje", socketId, nombreUsuario, sala, mensaje, fecha)
    }

    fun recuperarchat(): JSONArray? {
        socketChat.on("actualizarMensaje") { usuarioString ->
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