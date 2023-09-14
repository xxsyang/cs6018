package com.example.cs6018_project

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cs6018_project.databinding.FragmentSecondBinding


class secondFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val binding = FragmentSecondBinding.inflate(inflater)
        val viewModel : myViewModel by activityViewModels()

        binding.seekbarDiscrete.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.size.value= progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        viewModel.size.observe(viewLifecycleOwner) {
            binding.customView.setSize(it)
        }

        binding.buttonCircle.setOnClickListener {
            binding.customView.setShape(1)
            binding.buttonCircle.text = "◯ (current)"
            binding.buttonSquare.text = "□"
        }

        binding.buttonSquare.setOnClickListener {
            binding.customView.setShape(2)
            binding.buttonCircle.text = "◯"
            binding.buttonSquare.text = "□ (current)"
        }

        binding.buttonBlueColor.setOnClickListener {
            binding.customView.setColor(Color.BLUE)
        }

        binding.buttonRedColor.setOnClickListener {
            binding.customView.setColor(Color.RED)
        }

        binding.buttonBlackColor.setOnClickListener {
            binding.customView.setColor(Color.BLACK)
        }

        binding.buttonGreenColor.setOnClickListener {
            binding.customView.setColor(Color.GREEN)
        }

        binding.buttonYellowColor.setOnClickListener {
            binding.customView.setColor(Color.YELLOW)
        }

        binding.buttonCyanColor.setOnClickListener {
            binding.customView.setColor(Color.CYAN)
        }

        binding.buttonGaryColor.setOnClickListener {
            binding.customView.setColor(Color.GRAY)
        }
        
        viewModel.bitmap.observe(viewLifecycleOwner) {
            binding.customView.setBitMap(it)
        }

        return binding.root

    }
}