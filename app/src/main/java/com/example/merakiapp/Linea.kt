package com.example.merakiapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.ImageButton

class Linea(context: Context, btnIzquierda: ImageButton, btnDerecha: ImageButton, numeroActivity: Int, esTablet: Boolean): View(context) {
    // Se guardan las posiciones X e Y de los botones ImageButton pasados como argumento en variables
    var x1 = btnIzquierda.x
    var y1 = btnIzquierda.y
    var x2 = btnDerecha.x
    var y2 = btnDerecha.y

    var valor_esTablet = esTablet
    var x1_actividad_1 = 0F
    var x2_actividad_1 = 0F
    var y_actividad_1 = 0F

    var x1_actividad_2 = 0F
    var x2_actividad_2 = 0F
    var y_actividad_2 = 0F

    // Se guarda el numero de activity
    var numActivity = numeroActivity

    // Se sobre escribe el metodo onDraw
    override fun onDraw(canvas: Canvas) {
        // Se crea un objeto Paint
        val pincel = Paint()

        // Se configura el color del pincel
        pincel.setARGB(255,255,0,0)

        // Se establece el ancho de la línea
        pincel.strokeWidth = 10F

        // Comprobar si el dispositivo es Tablet o Smartphone
        if (valor_esTablet) {
            x1_actividad_1 = 155F
            x2_actividad_1 = 120F
            y_actividad_1 = 40F

            x1_actividad_2 = 130F
            x2_actividad_2 = 80F
            y_actividad_2 = 75F
        } else {
            x1_actividad_1 = 105F
            x2_actividad_1 = 40F
            y_actividad_1 = 45F

            x1_actividad_2 = 90F
            x2_actividad_2 = 25F
            y_actividad_2 = 70F
        }

        // Si el numero de actividad es 1, se dibuja una linea con los puntos x1, y1, x
        if (numActivity == 1) {
            canvas.drawLine(x1 + x1_actividad_1, y1 - y_actividad_1, x2 + x2_actividad_1, y2 - y_actividad_1, pincel)
        } else {
            canvas.drawLine(x1 + x1_actividad_2, y1 - y_actividad_2, x2 + x2_actividad_2, y2 - y_actividad_2, pincel)
        }
    }
}