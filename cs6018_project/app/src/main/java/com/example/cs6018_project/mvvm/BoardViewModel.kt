package com.example.cs6018_project.mvvm

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

fun <T> MutableLiveData<T>.trigger() {
    value = value
}

class BoardViewModel : ViewModel()  {
    // private val boardRepository = BoardRepository.getBoardData()
    private lateinit var canvas: Canvas

    private val _boardData = MutableLiveData<Board>()
    val boardData: LiveData<Board> = _boardData

    fun refreshBoardData() {
        Log.wtf("*", "Will exec refreshBoardData()")
        val board = BoardRepository.getBoardData()
        _boardData.value = board
        canvas = Canvas(_boardData.value!!.bitmap)
        Log.wtf("*", "refreshBoardData() executed")
    }

    fun setColor(color: Int) {
        _boardData.value?.color = color
        _boardData.trigger()
    }

    fun setSize(size: Int) {
        _boardData.value?.size = size
        _boardData.value?.radius = size.toFloat()
        _boardData.value?.squareLength = size
        _boardData.trigger()
    }

    fun setShape(v: Int) {
        _boardData.value?.shapeOfPen = v
        _boardData.trigger()
    }

    fun onTouch(x: Int, y: Int, eventType: Int) {
        Log.wtf("*", "onTouch()")

        val squarePen: Rect by lazy { Rect(x, y, x + boardData.value!!.squareLength, y + boardData.value!!.squareLength) }

        val paint = Paint()
        paint.color = _boardData.value!!.color;

        when (eventType) {
            MotionEvent.ACTION_DOWN -> {
                when (boardData.value!!.shapeOfPen) {
                    1 -> { // 1: circle
                        Log.d("TAG", "xValue: $x")
                        Log.d("TAG", "yValue: $y")
                        canvas.drawCircle(x.toFloat(), y.toFloat(), boardData.value!!.radius, paint)
                    }

                    2 -> {
                        canvas.drawRect(squarePen, paint)
                    }
                }
                _boardData.trigger()
            }

            MotionEvent.ACTION_MOVE -> {
                when (boardData.value!!.shapeOfPen) {
                    1 -> {
                        canvas.drawCircle(x.toFloat(), y.toFloat(), boardData.value!!.radius, paint)
                    }

                    2 -> {
                        canvas.drawRect(squarePen, paint)
                    }
                }
                _boardData.trigger()
            }
        }
    }
}