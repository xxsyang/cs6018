package com.example.cs6018_project.mvvm

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.OnReceiveContentListener
import android.view.View
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow


class BoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {


    private var bitmap = Bitmap.createBitmap(2160, 3840, Bitmap.Config.ARGB_8888)
    lateinit var sensorManager: SensorManager
    lateinit var gravity:Sensor
   // lateinit var gravityFlow : Flow<FloatArray>


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

        //if(onTouchListener != null) {
        onTouchListener(x, y, event.action)
        //}

        invalidate()

        return true
    }

    private lateinit var onTouchListener: (Int, Int, Int) -> Unit


    fun setTouchListener(touchListener: (Int, Int, Int) -> Unit) {
        onTouchListener = touchListener
    }





        //initialize sensor and sensor manager
    fun initSensor(){
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!!

    }


    //get the data from sensor returns Flow<FloatArray>
    fun getGravityData(): Flow<FloatArray> {
        return channelFlow {
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event !== null) {
                        Log.e("Sensor event!", event.values.toString())
                        var success = channel.trySend(event.values.copyOf()).isSuccess
                        Log.e("success?", success.toString())
                    }
                    else{
                        Log.e("Sensor event!", "NO SENSOR")
                    }

                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

                }
            }
            sensorManager.registerListener(listener, gravity, SensorManager.SENSOR_DELAY_GAME)

            awaitClose {
                sensorManager.unregisterListener(listener)
            }
        }
    }
    
}
