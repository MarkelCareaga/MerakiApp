package com.example.merakiapp

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private lateinit var mensajeRotarPantalla: String

interface Dialogos {

    companion object {
        // --------------- LISTA DE TITULOS ---------------
        val tituloExplicacion = "INFO"
        val tituloJuegos = "OBJETIVO DEL JUEGO"
        val tituloVideo = "VIDEO"
        val tituloFinal = "BIEN HECHO"
        val tituloDialogFallo = "VUELVE A INTENTARLO"
        val tituloRotarPantalla = "INFO"
        val permisoDenegado= "PERMISOS NO ACEPTADOS"

        // --------------- LISTA DE MENSAJES ---------------
        val mensajeInicio = "Meraki es una aplicación que te permite disfrutar de una serie de juegos, " +
                "mientrás exploras el pueblo de Bermeo.\n\n" +
                "En el 'Modo Explorador', puedes completar los juegos solo cuando estás en cada una de " +
                "las ubicaciones que aparecen en el mapa.\n(Uso obligatorio del GPS)\n\n" +
                "En el 'Modo Libre', puedes acceder a todos los juegos desde el principio y no necesitas " +
                "activar el GPS."
        val mensajePermisos= "Por favor acepte los permises de ubicacion para acceder a la siguiente pantalla."
        val mensajeExplicacion = "Lee y/o escucha atentamente la explicación. Te servirá de ayuda para completar correctamente el juego."
        val mensajeFeriaPescado = "Une las imágenes con sus nombres correspondientes.\n" +
                "Para unir dichos elementos, pulsa los botones circulares."
        val mensajeBadatoz = "Rellena el puzzle con las piezas disponibles. \n" +
                "Para arrastrar una pieza, manten pulsado unos segundos sobre ella."
        val mensajeGaztelugatxe = "Selecciona las respuestas correctas."
        val mensajeOlatua = "Une los idiomas con sus palabras correspondientes.\n" +
                "Para unir dichos elementos, pulsa los botones circulares."
        val mensajePuertaSanJuan = "Introduce la respuesta correcta."
        val mensajeSopaLetras = "Busca las 7 palabras en la sopa de letras."
        val mensajeVideoFeriaPescado = "Video sobre la Feria del Pescado."
        val mensajeXixili = "Rellena los huecos del párrafo con las frases correctas. \n" +
                "Para arrastrar una frase, manten pulsado unos segundos sobre ella."
        val mensajeFinal = "Has completado correctamente todas las actividades."
        val mensajeDialogFallo = "El resultado del juego es incorrecto."
        val mensajeAceptar = "Aceptar"
        val rotacionActivada = "La rotación del dispositivo está activada."
        val rotacionDesactivada = "La rotación del dispositivo está desactivada."
    }

    // Función para mostrar un mensaje relacionado con juegos o explicaciones
    fun mostrar_dialog(context: Context, titulo: String, mensaje: String) {
        val dialog_info = MaterialAlertDialogBuilder(context)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton(mensajeAceptar, null)
            .setCancelable(false)
            .create()
            .apply {
                show()
            }
    }

    // Función para mostrar información relacionada con la rotación de la pantalla
    fun mostrar_info_pantalla(context: Context, rotarPantalla: Boolean) {
        // Controlar el mensaje a mostrar
        if (rotarPantalla) {
            mensajeRotarPantalla = rotacionActivada
        } else {
            mensajeRotarPantalla = rotacionDesactivada
        }

        val dialog_pantalla = MaterialAlertDialogBuilder(context)
            .setTitle(tituloRotarPantalla)
            .setMessage(mensajeRotarPantalla)
            .setPositiveButton(mensajeAceptar, null)
            .setCancelable(false)
            .create()
            .apply {
                show()
            }
    }

    // Función para mostrar un aviso de que el resultado del juego es incorrecto
    fun mostrar_fallo_juego(context: Context) {
        val dialog_game_over = MaterialAlertDialogBuilder(context)
            .setTitle(tituloDialogFallo)
            .setMessage(mensajeDialogFallo)
            .setPositiveButton(mensajeAceptar, null)
            .setCancelable(false)
            .create()
            .apply {
                show()
            }
    }

}