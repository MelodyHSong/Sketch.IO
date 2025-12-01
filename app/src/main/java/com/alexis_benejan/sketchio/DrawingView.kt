/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: DrawingView.kt
☆ Date: 2025-11-30
☆
*/

package com.alexis_benejan.sketchio

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.createBitmap
import kotlin.math.abs

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var drawPath: Path = Path()
    private var drawPaint: Paint = Paint()
    private var canvasPaint: Paint

    private lateinit var drawCanvas: Canvas
    private lateinit var canvasBitmap: Bitmap

    private var currentColor: Int = Color.BLACK
    private var brushSize: Float = 10f

    private var currentX: Float = 0f
    private var currentY: Float = 0f
    private val touchTolerance = 4f

    init {
        drawPaint.apply {
            color = currentColor
            isAntiAlias = true
            isDither = true
            strokeWidth = brushSize
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }

        canvasPaint = Paint(Paint.DITHER_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (::canvasBitmap.isInitialized) canvasBitmap.recycle()
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap)
        drawCanvas.drawColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap, 0f, 0f, canvasPaint)
        canvas.drawPath(drawPath, drawPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart(touchX, touchY)
            MotionEvent.ACTION_MOVE -> touchMove(touchX, touchY)
            MotionEvent.ACTION_UP -> touchUp()
            else -> return false
        }

        invalidate()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun touchStart(x: Float, y: Float) {
        drawPath.reset()
        drawPath.moveTo(x, y)
        currentX = x
        currentY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = abs(x - currentX)
        val dy = abs(y - currentY)

        if (dx >= touchTolerance || dy >= touchTolerance) {
            drawPath.quadTo(currentX, currentY, (x + currentX) / 2, (y + currentY) / 2)
            currentX = x
            currentY = y
        }
    }

    private fun touchUp() {
        drawPath.lineTo(currentX, currentY)
        drawCanvas.drawPath(drawPath, drawPaint)
        drawPath.reset()
    }

    /*
    ☆
    ☆ Metodos requeridos por la aplicación
    ☆
    */

    /** Establece un nuevo color para el pincel de dibujo. */
    fun setBrushColor (newColor: Int) {
        currentColor = newColor
        drawPaint.color = currentColor
    }

    /** Establece un nuevo tamaño para el pincel de dibujo. */
    fun setBrushSize (newSize: Float) {
        brushSize = newSize
        drawPaint.strokeWidth = brushSize
    }

    /** Borra todo el contenido del lienzo. */
    fun clear() {
        drawCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    /** Devuelve el bitmap que contiene el dibujo actual. */
    fun getDrawingBitmap(): Bitmap {
        return canvasBitmap
    }
}