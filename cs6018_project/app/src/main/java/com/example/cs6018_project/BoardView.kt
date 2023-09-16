package com.example.cs6018_project

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class BoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var bitmap = Bitmap.createBitmap(2160, 3840, Bitmap.Config.ARGB_8888)

    init {
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, 0f, 0f, null)

        Log.wtf("*", "onDraw()")
    }

    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
        Log.wtf("*", "setBitmap()")
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        val x = event!!.x.toInt()
        val y = event.y.toInt()

        if(onTouchListener != null) {
            onTouchListener(x, y, event.action)
        }

        invalidate()

        return true
    }

    private lateinit var onTouchListener: (Int, Int, Int) -> Unit

    fun setTouchListener(touchListener: (Int, Int, Int) -> Unit) {
        onTouchListener = touchListener
    }


}


