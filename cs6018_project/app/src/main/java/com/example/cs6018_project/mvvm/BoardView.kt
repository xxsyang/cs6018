package com.example.cs6018_project.mvvm

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow


class BoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private lateinit var sensorManager: SensorManager
    private lateinit var gravity: Sensor

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
    fun initSensor() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!!
    }

    // get the data from sensor returns Flow<FloatArray>
    fun getGravityData(): Flow<FloatArray> {
        return channelFlow {
            val listener = object: SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event !== null) {
                        Log.e("Sensor event!", event.values.toString())
                        val success = channel.trySend(event.values.copyOf()).isSuccess
                        Log.e("success?", success.toString())
                    }
                    else {
                        Log.e("Sensor event!", "NO SENSOR")
                    }
                }
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
            }
            sensorManager.registerListener(listener, gravity, SensorManager.SENSOR_DELAY_GAME)
            awaitClose {
                sensorManager.unregisterListener(listener)
            }
        }
    }

}
