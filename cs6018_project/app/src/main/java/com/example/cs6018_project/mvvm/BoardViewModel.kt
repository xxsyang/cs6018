package com.example.cs6018_project.mvvm

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
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
import kotlinx.coroutines.flow.channelFlow

fun <T> MutableLiveData<T>.trigger() {
    value = value
}

class BoardViewModel : ViewModel()  {
    // private val boardRepository = BoardRepository.getBoardData()
    private lateinit var canvas: Canvas

    var _boardData = MutableLiveData<Board>()
    val boardData: LiveData<Board> = _boardData

//    lateinit var gravityFlow : Flow<FloatArray>
//    var gravityFlow = MutableLiveData<FloatArray>()
//




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


    fun onClick(x: Float,y: Float,eventType: Int){

        var yCoordinate =y
        var xCoordinate =x
        if (x>0){
            xCoordinate+=5

        }
        if (x < 0){
            xCoordinate-=5
        }

        if (y>0){
            yCoordinate+=5

        }
        if (y < 0){
            yCoordinate-=5
        }
        if(yCoordinate < 0){
            yCoordinate = 0f

        }

        if(xCoordinate <0){
            xCoordinate = 0f

        }

        onTouch(xCoordinate as Int,yCoordinate as Int,eventType)

    }





}


