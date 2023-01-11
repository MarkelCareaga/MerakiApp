package com.example.merakiapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.ImageButton

class Linea(context: Context, btnIzquierda: ImageButton, btnDerecha: ImageButton, numeroActivity: Int): View(context) {
    // se guardan las posiciones x y y de los botones ImageButton pasados como argumento en variables
    var x1 = btnIzquierda.x
    var y1 = btnIzquierda.y
    var x2 = btnDerecha.x
    var y2 = btnDerecha.y

    //se guarda el numero de activity
    var numActivity = numeroActivity

    //se sobre escribe el metodo onDraw
    override fun onDraw(canvas: Canvas) {
        //se crea un objeto Paint
        val pincel = Paint()
        // se configura el color del pincel
        pincel.setARGB(255,255,0,0)
        // se establece el ancho de la línea
        pincel.strokeWidth = 10F
        // si el numero de actividad es 1, se dibuja una linea con los puntos x1, y1, x
        if (numActivity == 1) {
            canvas.drawLine(x1+160F, y1-45F, x2+100F, y2-45F, pincel)
        } else {
            canvas.drawLine(x1+125F, y1-80F, x2+75F, y2-80F, pincel)
        }
    }
}