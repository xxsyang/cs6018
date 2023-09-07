package com.example.cs6018_project

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class myViewModel : ViewModel()  {
    private var _width = 1
    private var _height = 1

    private val _bitmap: MutableLiveData<Bitmap> = MutableLiveData(Bitmap.createBitmap(1440, 2990, Bitmap.Config.ARGB_8888))
    private val _color : MutableLiveData<Color> = MutableLiveData(Color.valueOf(1f, 1f, 0f))
    var size:MutableLiveData<Int> = MutableLiveData<Int>()

    val bitmap = _bitmap as LiveData<Bitmap>
    val color  = _color as LiveData<Color>

    fun pickColor(){
        with(Random.Default) {
            _color.value = Color.valueOf(nextFloat(), nextFloat(), nextFloat())
        }
    }

    fun DrawBitMap(width: Int, height: Int) {
        if (width == _width && _height == height) {
            return
        }
        _bitmap.value = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        _height = height
        _width = width
    }

}