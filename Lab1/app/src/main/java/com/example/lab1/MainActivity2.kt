package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val string = intent.getStringExtra("string")

        val textView: TextView = findViewById(R.id.textView)
        Log.d("TEST", textView.toString());
        textView.text = string


    }
}