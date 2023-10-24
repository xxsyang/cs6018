package com.example.cs6018_project.mvvm

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow

fun <T> MutableLiveData<T>.trigger() {
    value = value
}

class BoardViewModel : ViewModel()  {
    // private val boardRepository = BoardRepository.getBoardData()
    private lateinit var canvas: Canvas

    var _boardData = MutableLiveData<Board>()
    val boardData: LiveData<Board> = _boardData


    fun refreshBoardData() {
        Log.wtf("*", "Will exec refreshBoardData()")
        val board = BoardRepository.getBoardData()
        _boardData.value = board
        canvas = Canvas(_boardData.value!!.bitmap)
        Log.wtf("*", "refreshBoardData() executed")
    }

    fun getCurrentBitmap(): Bitmap {
        return _boardData.value!!.bitmap
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


    fun sensorDraw(x: Float,y: Float){
       var  xcdnt =x
        var ycdnt = y
        var bound = canvas.clipBounds
        Log.e("CanvasBounds","Width "+ bound.width().toString()+"Hieght "+bound.height().toString())

       if(y > 2000) {
           ycdnt=2000f
       }
        if(x >1000 ) {
            xcdnt= 1000f
        }
        if(x <90){
            xcdnt=90f

        }
        if(y <300){
            ycdnt=300f

        }
        xcdnt+=10
        ycdnt+=10

        val paint = Paint()
        paint.color = _boardData.value!!.color

        canvas.drawCircle(xcdnt, ycdnt, boardData.value!!.radius, paint)
        _boardData.trigger()

    }

}


