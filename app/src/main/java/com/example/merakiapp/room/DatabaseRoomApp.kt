package com.example.merakiapp.room

import android.app.Application
import androidx.room.Room

class DatabaseRoomApp: Application() {
    companion object{
        lateinit var database:UsuarioDB
    }

    override fun onCreate() {
        super.onCreate()
        database= Room
            .databaseBuilder(this,UsuarioDB::class.java, UsuarioDB.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }
}