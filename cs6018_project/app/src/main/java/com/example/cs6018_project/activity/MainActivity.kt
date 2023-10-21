package com.example.cs6018_project.activity

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.cs6018_project.R
import com.example.cs6018_project.databinding.ActivityMainBinding
import com.example.cs6018_project.fragment.FirstFragment
import com.example.cs6018_project.fragment.SavedBoardFragment
import com.example.cs6018_project.fragment.SecondFragment
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        fun getSeM():SensorManager{
            return sensorManager
        }


        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        //val navController = navHostFragment.navController

        //navController.navigate(R.id.action_firstFragment_to_savedBoardFragment)

        /*
        try {
            binding.fragmentContainerView.getFragment<FirstFragment>().setButtonFunction {

                // val secondFragment = SecondFragment()
                val secondFragment = SavedBoardFragment()
                val transaction = this.supportFragmentManager.beginTransaction()

                transaction.replace(R.id.fragmentContainerView, secondFragment, "draw_tag")
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
         */

        setContentView(binding.root)
    }
}