package com.example.merakiapp.room


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuarios")
class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id:Long,
    @ColumnInfo(name = "Nombre Usuario")
    @NonNull
    var nombreusuario:String,
    @ColumnInfo(name = "Pasos Usuario")
    @NonNull
    var pasosUsuario: Int,
    @ColumnInfo(name = "Imagen")
    @NonNull
    var imagen: Int,

    ){}