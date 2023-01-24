package com.example.merakiapp

import android.content.Context
import android.content.Intent
import com.example.merakiapp.explicaciones.ExplicacionesActivity

// La Interfaz "Explicaciones" se encarga de recoger los recursos y abrir su
// correspondiente pantalla explicativa.
interface Explicaciones {

    // Función para recoger los siguientes recursos: pantalla, audio y fondo.
    fun abrirExplicacion(context: Context, pantallaSeleccionada: String, audioSeleccionado: Int, fondoSeleccionado: Int): Intent {

        // Selecciona el texto explicativo, dependiendo de la pantalla recogida.
        var textoSeleccionado = ""
        when(pantallaSeleccionada) {
            Recursos.pantalla_Introduccion -> textoSeleccionado = Recursos.texto_Introduccion
            Recursos.pantalla_PuertaSanJuan -> textoSeleccionado = Recursos.texto_PuertaSanJuan
            Recursos.pantalla_Badatoz -> textoSeleccionado = Recursos.texto_Badatoz
            Recursos.pantalla_FeriaPescado -> textoSeleccionado = Recursos.texto_FeriaPescado
            Recursos.pantalla_Olatua -> textoSeleccionado = Recursos.texto_Olatua
            Recursos.pantalla_Xixili -> textoSeleccionado = Recursos.texto_Xixili
            Recursos.pantalla_Izaro -> textoSeleccionado = Recursos.texto_Izaro
            Recursos.pantalla_Gaztelugatxe -> textoSeleccionado = Recursos.texto_Gaztelugatxe
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