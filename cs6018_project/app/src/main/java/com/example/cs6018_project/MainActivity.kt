package com.example.cs6018_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cs6018_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)


        binding.fragmentContainerView.getFragment<firstFragment>().setButtonFunction {
            val secondFragment = secondFragment()
            val transaction = this.supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragmentContainerView, secondFragment, "draw_tag")
            transaction.addToBackStack(null)
            transaction.commit()
        }
        setContentView(binding.root)
    }
}