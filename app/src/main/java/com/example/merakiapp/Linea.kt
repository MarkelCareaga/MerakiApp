package com.example.merakiapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.ImageButton

const val tabletFeriaX1 = 155F
const val tabletFeriaX2 = 120F
const val tabletFeriaY = 40F
const val tabletOlatuaX1 = 130F
const val tabletOlatuaX2 = 80F
const val tabletOlatuaY = 75F
const val movilFeriaX1 = 105F
const val movilFeriaX2 = 40F
const val movilFeriaY = 5F
const val movilOlatuaX1 = 90F
const val movilOlatuaX2 = 25F
const val movilOlatuaY = 80F
const val anchuraLinea = 10F

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
        pincel.strokeWidth = anchuraLinea

        // Comprobar si el dispositivo es Tablet o Smartphone
        if (valor_esTablet) {
            // Feria del Pescado
            x1_actividad_1 = tabletFeriaX1
            x2_actividad_1 = tabletFeriaX2
            y_actividad_1 = tabletFeriaY

            // Olatua Estatua
            x1_actividad_2 = tabletOlatuaX1
            x2_actividad_2 = tabletOlatuaX2
            y_actividad_2 = tabletOlatuaY
        } else {
            // Feria del Pescado
            x1_actividad_1 = movilFeriaX1
            x2_actividad_1 = movilFeriaX2
            y_actividad_1 = movilFeriaY

            // Olatua Estatua
            x1_actividad_2 = movilOlatuaX1
            x2_actividad_2 = movilOlatuaX2
            y_actividad_2 = movilOlatuaY
        }

        // Si el numero de actividad es 1, se dibuja una linea con los puntos x1, y1, x
        if (numActivity == 1) {
            canvas.drawLine(x1 + x1_actividad_1, y1 - y_actividad_1, x2 + x2_actividad_1, y2 - y_actividad_1, pincel)
        } else {
            canvas.drawLine(x1 + x1_actividad_2, y1 - y_actividad_2, x2 + x2_actividad_2, y2 - y_actividad_2, pincel)
        }
    }
}