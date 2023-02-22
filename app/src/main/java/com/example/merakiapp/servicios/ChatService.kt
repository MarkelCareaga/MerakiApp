package com.example.merakiapp.servicios

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.merakiapp.databinding.FragmentChatBinding
import io.socket.client.IO

var socketChat = IO.socket("https://chatglobalmeraki.glitch.me")

class ChatService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        socketChat.connect()

        val name=intent?.getStringExtra("nombreUsuario")
        val sala=intent?.getStringExtra("sala")

        socketChat.emit("userConnection",name,sala)


        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        socketChat.emit("userDisconnect")
        super.onDestroy()
    }



    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}