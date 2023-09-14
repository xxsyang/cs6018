package com.example.cs6018_project

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Available shapes in Canvas.java:
 * drawCircle (✅)
 * drawRect (✅)
 * drawOval (decided not to do)
 * drawLines (4 points -> shape)
 */
class customView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var bitmap = Bitmap.createBitmap(1440, 20, Bitmap.Config.ARGB_8888)
    private var bitmapCanvas = Canvas(bitmap)
    private val paint = Paint()
    private val rect: Rect by lazy { Rect(0, 0, width, height) }
    private var radius = 10f
    private var square_length = 20 // for size of square pen shape

    private var shape_of_pen = 1 // 1 (default): circle, 2: square

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, null, rect, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        super.onTouchEvent(event)

        val x = event!!.x.toInt()
        val y = event.y.toInt()

        // square pen shape
        val square_pen: Rect by lazy { Rect(x, y, x + square_length, y + square_length) }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                when (shape_of_pen) {
                    1 -> { // 1: circle
                        bitmapCanvas.drawCircle(x.toFloat(), y.toFloat(), radius, paint)
                    }
                    2 -> { // 2: square
                        bitmapCanvas.drawRect(square_pen, paint)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                when (shape_of_pen) {
                    1 -> { // 1: circle
                        bitmapCanvas.drawCircle(x.toFloat(), y.toFloat(), radius, paint)
                    }
                    2 -> { // 2: square
                        bitmapCanvas.drawRect(square_pen, paint)
                    }
                }
            }
        }
        invalidate()
        return true
    }

    fun setBitMap(bitmap: Bitmap) {
        this.bitmap = bitmap
        this.bitmapCanvas = Canvas(bitmap)
    }

    fun setColor(color: Int) {
        paint.color = color
    }

    fun setSize(size: Int) {
        radius = size.toFloat()
    }

    // set pen shape (1: circle, 2: square)
    fun setShape(size: Int) {
        shape_of_pen = size
    }
}


