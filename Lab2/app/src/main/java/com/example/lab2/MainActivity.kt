package com.example.lab2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)


        binding.fragmentContainerView.getFragment<navigatesFragment>().setButtonFunction {
            val secondFragment = secondFragment()
            val transaction = this.supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragmentContainerView, secondFragment, "draw_tag")
            transaction.addToBackStack(null)
            transaction.commit()
        }
        setContentView(binding.root)
    }
}