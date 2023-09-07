package com.example.cs6018_project

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class customView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var bitmap = Bitmap.createBitmap(1440, 20, Bitmap.Config.ARGB_8888)
    private var bitmapCanvas = Canvas(bitmap)
    private val paint = Paint()
    private val rect: Rect by lazy { Rect(0, 0, width, height) }
    private var radius = 10f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, null, rect, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        super.onTouchEvent(event)

        val x = event!!.x.toInt()
        val y = event.y.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                bitmapCanvas.drawCircle(x.toFloat(), y.toFloat(), radius, paint)
            }

            MotionEvent.ACTION_MOVE -> {
                bitmapCanvas.drawCircle(x.toFloat(), y.toFloat(), radius, paint)
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
        radius += size
    }
}


