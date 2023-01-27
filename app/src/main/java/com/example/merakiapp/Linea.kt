package com.example.merakiapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.ImageButton

@SuppressLint("ViewConstructor")
class Linea(context: Context, btnIzquierda: ImageButton, btnDerecha: ImageButton, numeroActivity: Int, esTablet: Boolean): View(context) {
    // Se guardan las posiciones X e Y de los botones ImageButton pasados como argumento en variables
    private var x1 = btnIzquierda.x
    private var y1 = btnIzquierda.y
    private var x2 = btnDerecha.x
    private var y2 = btnDerecha.y

    private var valor_esTablet = esTablet
    private var x1_actividad_1 = 0F
    private var x2_actividad_1 = 0F
    private var y_actividad_1 = 0F

    private var x1_actividad_2 = 0F
    private var x2_actividad_2 = 0F
    private var y_actividad_2 = 0F

    // Se guarda el numero de activity
    private var numActivity = numeroActivity

    // Se sobre escribe el metodo onDraw
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        // Se crea un objeto Paint
        val pincel = Paint()

        // Se configura el color del pincel
        pincel.setARGB(255,255,0,0)

        // Se establece el ancho de la línea
        pincel.strokeWidth = 10F

        // Comprobar si el dispositivo es Tablet o Smartphone
        if (valor_esTablet) {
            // Feria del Pescado
            x1_actividad_1 = 155F
            x2_actividad_1 = 120F
            y_actividad_1 = 40F

            // Olatua Estatua
            x1_actividad_2 = 130F
            x2_actividad_2 = 80F
            y_actividad_2 = 75F
        } else {
            // Feria del Pescado
            x1_actividad_1 = 105F
            x2_actividad_1 = 40F
            y_actividad_1 = 5F

            // Olatua Estatua
            x1_actividad_2 = 90F
            x2_actividad_2 = 25F
            y_actividad_2 = 80F
        }

        // Si el numero de actividad es 1, se dibuja una linea con los puntos x1, y1, x
        if (numActivity == 1) {
            canvas.drawLine(x1 + x1_actividad_1, y1 - y_actividad_1, x2 + x2_actividad_1, y2 - y_actividad_1, pincel)
        } else {
            canvas.drawLine(x1 + x1_actividad_2, y1 - y_actividad_2, x2 + x2_actividad_2, y2 - y_actividad_2, pincel)
        }
    }
}