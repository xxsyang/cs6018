package com.example.cs6018_project

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class myViewModel : ViewModel()  {
    private var _width = 1
    private var _height = 1

    private val _bitmap: MutableLiveData<Bitmap> = MutableLiveData(Bitmap.createBitmap(1440, 2990, Bitmap.Config.ARGB_8888))
    private val _color : MutableLiveData<Color> = MutableLiveData(Color.valueOf(0f, 0f, 0f))
    var size:MutableLiveData<Int> = MutableLiveData<Int>()

    val bitmap = _bitmap as LiveData<Bitmap>

}