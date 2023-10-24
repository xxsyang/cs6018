package com.example.cs6018_project.fragment

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.Bitmap
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.cs6018_project.DynamicConfig
import com.example.cs6018_project.R
import com.example.cs6018_project.activity.MainActivity
import com.example.cs6018_project.databinding.FragmentSecondBinding
import com.example.cs6018_project.mvvm.BoardRepository
import com.example.cs6018_project.mvvm.BoardViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class SecondFragment : Fragment() {


    lateinit var viewModel : BoardViewModel
    private var yCoordinate = mutableFloatStateOf(0.0f)
    private var xCoordinate =mutableFloatStateOf(0.0f)

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {




        // Inflate the layout for this fragment
        val binding = FragmentSecondBinding.inflate(inflater)



        val viewModelFromActivityViewModels : BoardViewModel by activityViewModels()



        viewModel = viewModelFromActivityViewModels

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.refreshBoardData()


        binding.seekbarDiscrete.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.wtf("*", "onProgressChanged()")
                viewModel.setSize(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
            override fun onStopTrackingTouch(seekBar: SeekBar?) { }

        })

            //added this
        binding.buttonSensor.setOnClickListener {
            binding.boardView.initSensor()



            binding.boardView.getGravityData().asLiveData().observe(viewLifecycleOwner){


                if (it[0]>0){


                    xCoordinate.value+=10

                }
                if (it[0] < 0){

                    xCoordinate.value-=5
                }

                if (it[1]>0){

                    yCoordinate.value+=5

                }
                if (it[1] < 0){
                    yCoordinate.value-=10
                }
                if(yCoordinate.value < 0)
                    yCoordinate.value = 0f
                if(xCoordinate.value <0)
                    xCoordinate.value = 0f

                binding.viewModel?.sensorDraw(xCoordinate.value,yCoordinate.value)

            }

            binding.buttonSensor.text = "SensorArt"

        }

        binding.buttonCircle.setOnClickListener {
            binding.viewModel?.setShape(1)
            binding.buttonCircle.text = getString(R.string.brush_round)
            binding.buttonSquare.text = "□"
        }

        binding.buttonSquare.setOnClickListener {
            binding.viewModel?.setShape(2)
            binding.buttonCircle.text = "◯"
            binding.buttonSquare.text = getString(R.string.brush_square)
        }

        binding.buttonBlueColor.setOnClickListener {
            binding.viewModel?.setColor(Color.BLUE)
        }

        binding.buttonRedColor.setOnClickListener {
            binding.viewModel?.setColor(Color.RED)
        }

        binding.buttonBlackColor.setOnClickListener {
            binding.viewModel?.setColor(Color.BLACK)
        }

        binding.buttonGreenColor.setOnClickListener {
            binding.viewModel?.setColor(Color.GREEN)
        }

        binding.buttonYellowColor.setOnClickListener {
            binding.viewModel?.setColor(Color.YELLOW)
        }

        binding.buttonCyanColor.setOnClickListener {
            binding.viewModel?.setColor(Color.CYAN)
        }

        binding.buttonGaryColor.setOnClickListener {
            binding.viewModel?.setColor(Color.GRAY)
        }



        viewModel.boardData.observe(viewLifecycleOwner) {
            // on board data change
            binding.boardView.setBitmap(it.bitmap)
            Log.wtf("*", "on board data change")
            Log.wtf("*", it.toString())
        }


        binding.boardView.setTouchListener { x, y, t -> viewModel.onTouch(x, y, t) }

        return binding.root
    }





    override fun onAttach(context: Context) {
        super.onAttach(context)

        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.wtf("*", "override fun handleOnBackPressed()")
                Log.wtf("*", "Path: " + DynamicConfig.savedBoardDirectory + File.separator + DynamicConfig.currentEditBoard)

                try {
                    // save image
                    ByteArrayOutputStream().use {
                        viewModel.getCurrentBitmap().compress(Bitmap.CompressFormat.PNG, 95, it)
                        var dao = DynamicConfig.database.itemDao()
                        var item = dao.findByName(DynamicConfig.currentEditBoard)
                        item.image = it.toByteArray()
                        dao.updateImage(item)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                remove()
                activity?.onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}