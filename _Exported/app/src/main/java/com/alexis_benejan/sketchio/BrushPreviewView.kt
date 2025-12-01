/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: BrushPreviewView.kt
☆ Date: 2025-11-30
☆
*/

package com.alexis_benejan.sketchio

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BrushPreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var lineWidth: Float = 10f
    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    fun setLineWidth(width: Float) {
        lineWidth = width
        invalidate() // Forzar que la vista se vuelva a dibujar
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = lineWidth / 2f
        canvas.drawCircle(centerX, centerY, radius, paint)
    }
}