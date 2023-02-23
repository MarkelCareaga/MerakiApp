package com.example.merakiapp.listas

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.merakiapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ListaDialogos {

    var mensajeRotarPantalla: Int = 0

    // Función para mostrar un mensaje relacionado con juegos o explicaciones
    fun mostrar_dialog(context: Context, titulo: Int, mensaje: Int) {
        // Introduce el contenido especificado en el Dialog
        val dialog_info = MaterialAlertDialogBuilder(context)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton(ListaRecursos.mensajeAceptar, null)
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
            mensajeRotarPantalla = ListaRecursos.rotacionActivada
        } else {
            mensajeRotarPantalla = ListaRecursos.rotacionDesactivada
        }

        // Introduce el contenido especificado en el Dialog
        val dialog_pantalla = MaterialAlertDialogBuilder(context)
            .setTitle(ListaRecursos.tituloRotarPantalla)
            .setMessage(mensajeRotarPantalla)
            .setPositiveButton(ListaRecursos.mensajeAceptar, null)
            .setCancelable(false)
            .create()
            .apply {
                show()
            }
    }

    // Función para mostrar que el resultado del juego es incorrecto
    fun mostrar_fallo_juego(context: Context) {
        // Introduce el contenido especificado en el Dialog
        val dialog_game_over = MaterialAlertDialogBuilder(context)
            .setTitle(ListaRecursos.tituloDialogFallo)
            .setMessage(ListaRecursos.mensajeDialogFallo)
            .setPositiveButton(ListaRecursos.mensajeAceptar, null)
            .setCancelable(false)
            .create()
            .apply {
                show()
            }
    }
}