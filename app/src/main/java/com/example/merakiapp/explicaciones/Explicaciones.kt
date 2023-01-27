package com.example.merakiapp.explicaciones

import android.content.Context
import android.content.Intent
import com.example.merakiapp.listas.ListaRecursos

// La Interfaz "Explicaciones" se encarga de recoger los recursos y abrir su
// correspondiente pantalla explicativa.
interface Explicaciones {

    // Función para recoger los siguientes recursos: pantalla, audio y fondo.
    fun abrirExplicacion(context: Context, pantallaSeleccionada: String): Intent {

        var textoSeleccionado = ""
        var audioSeleccionado = 0
        var fondoSeleccionado = 0

        when (pantallaSeleccionada) {
            ListaRecursos.pantalla_Introduccion -> {
                textoSeleccionado = ListaRecursos.texto_Introduccion
                audioSeleccionado = ListaRecursos.audio_Introduccion
                fondoSeleccionado = ListaRecursos.fondo_Introduccion
            }
            ListaRecursos.pantalla_PuertaSanJuan -> {
                textoSeleccionado = ListaRecursos.texto_PuertaSanJuan
                audioSeleccionado = ListaRecursos.audio_PuertaSanJuan
                fondoSeleccionado = ListaRecursos.fondo_PuertaSanJuan
            }
            ListaRecursos.pantalla_Badatoz -> {
                textoSeleccionado = ListaRecursos.texto_Badatoz
                audioSeleccionado = ListaRecursos.audio_Badatoz
                fondoSeleccionado = ListaRecursos.fondo_Badatoz
            }
            ListaRecursos.pantalla_FeriaPescado -> {
                textoSeleccionado = ListaRecursos.texto_FeriaPescado
                audioSeleccionado = ListaRecursos.audio_FeriaPescado
                fondoSeleccionado = ListaRecursos.fondo_FeriaPescado
            }
            ListaRecursos.pantalla_Olatua -> {
                textoSeleccionado = ListaRecursos.texto_Olatua
                audioSeleccionado = ListaRecursos.audio_Olatua
                fondoSeleccionado = ListaRecursos.fondo_Olatua
            }
            ListaRecursos.pantalla_Xixili -> {
                textoSeleccionado = ListaRecursos.texto_Xixili
                audioSeleccionado = ListaRecursos.audio_Xixili
                fondoSeleccionado = ListaRecursos.fondo_Xixili
            }
            ListaRecursos.pantalla_Izaro -> {
                textoSeleccionado = ListaRecursos.texto_Izaro
                audioSeleccionado = ListaRecursos.audio_Izaro
                fondoSeleccionado = ListaRecursos.fondo_Izaro
            }
            ListaRecursos.pantalla_Gaztelugatxe -> {
                textoSeleccionado = ListaRecursos.texto_Gaztelugatxe
                audioSeleccionado = ListaRecursos.audio_Gaztelugatxe
                fondoSeleccionado = ListaRecursos.fondo_Gaztelugatxe
            }
        }

        // Intent para abrir la activity Explicaciones
        val intent = Intent(context, ExplicacionesActivity::class.java)

        // Variables que se enviarán en el Intent
        intent.putExtra("pantallaSeleccionada", pantallaSeleccionada)
        intent.putExtra("audioSeleccionado", audioSeleccionado)
        intent.putExtra("fondoSeleccionado", fondoSeleccionado)
        intent.putExtra("textoSeleccionado", textoSeleccionado)

        // Devolver el Intent para poder iniciar la Activity deseada
        return intent
    }

}