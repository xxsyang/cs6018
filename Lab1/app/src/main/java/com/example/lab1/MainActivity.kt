package com.example.lab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.button5).setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        val bt = v as Button
        val string = bt.text.toString()
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("string_to_show_ac2", string)
        startActivity(intent)
    }

}

