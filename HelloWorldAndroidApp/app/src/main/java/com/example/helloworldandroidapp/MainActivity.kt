package com.example.helloworldandroidapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.view.View


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener(){
            findViewById<TextView>(R.id.text1).text = "GitHub: xxsyang"

            val textV = findViewById<TextView>(R.id.text1)

            textV.setTextColor(Color.parseColor( "#FF0000"))

        }

        findViewById<Button>(R.id.button2).setOnClickListener(){
            findViewById<TextView>(R.id.text1).text = "GitHub: jinyizh"

            val textV = findViewById<TextView>(R.id.text1)

            textV.setTextColor(Color.parseColor( "#FF0000"))

        }
    }


}