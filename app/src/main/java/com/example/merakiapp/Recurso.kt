package com.example.merakiapp

import android.content.Context

class Recurso(
    override val contexto: Context,
    val pantalla: String,
    val audio: Int,
    val fondo: Int) : RecursoAbstracto()