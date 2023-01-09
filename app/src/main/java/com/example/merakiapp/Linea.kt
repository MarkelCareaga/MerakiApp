package com.example.merakiapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.ImageButton

class Linea(context: Context, btnIzquierda: ImageButton, btnDerecha: ImageButton, numeroActivity: Int): View(context) {
    var x1 = btnIzquierda.x
    var y1 = btnIzquierda.y
    var x2 = btnDerecha.x
    var y2 = btnDerecha.y

    var numActivity = numeroActivity

    override fun onDraw(canvas: Canvas) {
        val pincel = Paint()
        pincel.setARGB(255,255,0,0)
        pincel.strokeWidth = 10F
        if (numActivity == 1) {
            canvas.drawLine(x1+160F, y1-45F, x2+100F, y2-45F, pincel)
        } else {
            canvas.drawLine(x1+125F, y1-80F, x2+75F, y2-80F, pincel)
        }
    }
}