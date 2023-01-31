package com.example.merakiapp.ui.ayuda

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Indica que esta clase puede ser embalada en un Parcel
@Parcelize
// Declara una propiedad "Pregunta" de tipo String?
// Declara una propiedad "Respuesta" de tipo String?
data class Pregunta(val Pregunta: Int?, val Respuesta: Int?):Parcelable