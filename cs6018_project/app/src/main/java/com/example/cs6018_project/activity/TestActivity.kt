package com.example.cs6018_project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cs6018_project.DynamicConfig
import com.example.cs6018_project.R
import java.io.File

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        File(DynamicConfig.savedBoardDirectory).list().toList().iterator().forEach {
            var path = DynamicConfig.savedBoardDirectory + File.separator + it
            File(path).delete()
        }

        Toast.makeText(applicationContext, "OK", Toast.LENGTH_SHORT).show()

        finish()
    }
}