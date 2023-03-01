package com.example.merakiapp.servicios

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.merakiapp.ui.chat.ChatFragment
import com.example.merakiapp.ui.chat.mensajes.Mensajes
import io.socket.client.IO
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Thread.sleep

class ServicioChat : Service() {

    companion object {
        val socketChat = IO.socket("https://merakiapp-chatglobal.glitch.me")
        private var jsonarray: JSONArray? = null
        var mensajes: MutableList<Mensajes> = mutableListOf()
        var socketId: String = ""

        // Envia el usuario al servidor, donde se almacena en un array
        fun usuario(nombreUsuario: String) {
            socketChat.emit("usuario", nombreUsuario)
        }

        /*
        Envia la sala al servidor, donde se comprueba el identificador y la sala pertenecientes al
        usuario logeado.
        Además, comprueba si los mensajes existentes se han enviado hace más de 24 horas. En caso
        afirmativo, se eliminarán dichos mensajes.
        */
        fun sala(nombreUsuario: String, sala: String) {
            socketChat.emit("sala", sala, nombreUsuario)
        }

        // Envia el mensaje al servidor, donde se almacena en un array
        fun enviarmensaje(nombreUsuario: String, sala: String, mensaje: String, fecha: String) {
            socketChat.emit("EnviarMensaje", nombreUsuario, sala, mensaje, fecha)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Comprueba si se ha conectado con el servidor
        if (socketChat.connected()) {
            // Comprueba si se han recibido los mensajes desde el servidor
            socketChat.on("actualizarMensaje") { usuarioString ->
                mensajes.removeAll(mensajes)
                // Pasamos a un JSONarray todos los usuarios
                jsonarray = usuarioString[0] as JSONArray
                if (jsonarray != null) {
                    (0 until jsonarray!!.length()).forEach {
                        // Insertar datos en la lista
                        val objeto: JSONObject = jsonarray!!.getJSONObject(it)
                        if (objeto["sala"] == ChatFragment.sala) {
                            mensajes.add(
                                Mensajes(
                                    objeto["id"].toString(),
                                    objeto["nombreUsuario"].toString(),
                                    objeto["sala"].toString(),
                                    objeto["mensaje"].toString(),
                                    objeto["fecha"].toString()
                                )
                            )
                        }
                    }
                }

                // Envia un aviso al Broadcast, ubicado en el ChatFragment
                val intent = Intent("mensajes")
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
            }
            socketId = socketChat.id()

        } else {
            // Se activa la conexión con el servidor
            socketChat.connect()
            // Comprueba si se han recibido los mensajes desde el servidor
            socketChat.on("actualizarMensaje") { usuarioString ->
                mensajes.removeAll(mensajes)
                // Pasamos a un JSONarray todos los usuarios
                jsonarray = usuarioString[0] as JSONArray
                if (jsonarray != null) {
                    (0 until jsonarray!!.length()).forEach {
                        // Insertar datos en la lista
                        val objeto: JSONObject = jsonarray!!.getJSONObject(it)
                        if (objeto["sala"] == ChatFragment.sala) {
                            mensajes.add(
                                Mensajes(
                                    objeto["id"].toString(),
                                    objeto["nombreUsuario"].toString(),
                                    objeto["sala"].toString(),
                                    objeto["mensaje"].toString(),
                                    objeto["fecha"].toString()
                                )
                            )
                        }
                    }
                    socketId = socketChat.id()
                }

                // Envia un aviso al Broadcast, ubicado en el ChatFragment
                val intent = Intent("mensajes")
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        socketChat.disconnect()
        applicationContext?.getSharedPreferences("datosUsuario", 0)!!.edit().putString("nombre", "")
            .apply()
        applicationContext?.getSharedPreferences("datosUsuario", 0)!!.edit().putString("sala", "")
            .apply()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}