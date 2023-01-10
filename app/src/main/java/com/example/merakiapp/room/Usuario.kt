package com.example.merakiapp.room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id:Long,
    @ColumnInfo(name = "Nombre Usuario")
    var nombreusuario:String,
    @ColumnInfo(name = "Pasos Usuario")
    var pasosUsuario: Int,
    @ColumnInfo(name = "Icono")
    var icono: Bitmap?,
    @ColumnInfo(name = "Nombre Usuario Adversario")
    var nombreUsuarioAdversario: String?,
    @ColumnInfo(name = "Pasos Usuario Adversario")
    var pasosUsuarioAdversario:Int,
    @ColumnInfo(name = "Icono Adversario")
    var iconoAdversario: Bitmap?,

    ) {}