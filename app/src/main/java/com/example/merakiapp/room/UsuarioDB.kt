package com.example.merakiapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class], version = 1)

abstract class UsuarioDB: RoomDatabase() {

        abstract val usuarioDao:UsuarioDAO

        companion object{
            const val DATABASE_NAME = "usuarios"
        }
}