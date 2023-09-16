package com.example.cs6018_project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cs6018_project.R
import com.example.cs6018_project.databinding.ActivityMainBinding
import java.lang.Exception
import com.example.cs6018_project.fragment.FirstFragment
import com.example.cs6018_project.fragment.SecondFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        try {
            binding.fragmentContainerView.getFragment<FirstFragment>().setButtonFunction {
                val secondFragment = SecondFragment()
                val transaction = this.supportFragmentManager.beginTransaction()

                transaction.replace(R.id.fragmentContainerView, secondFragment, "draw_tag")
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        setContentView(binding.root)
    }
}