package com.example.cs6018_project.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cs6018_project.R
import com.example.cs6018_project.databinding.FragmentSecondBinding
import com.example.cs6018_project.BoardViewModel


class SecondFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val binding = FragmentSecondBinding.inflate(inflater)
        val viewModel : BoardViewModel by activityViewModels()


        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.refreshBoardData()

        viewModel.boardData.observe(viewLifecycleOwner) {
            // on board data change
            binding.boardView.setBitmap(it.bitmap)
            Log.wtf("*", "on board data change")
            Log.wtf("*", it.toString())
        }

        binding.boardView.setTouchListener { x, y, t -> viewModel.onTouch(x, y, t) }

        binding.seekbarDiscrete.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.wtf("*", "onProgressChanged()")
                viewModel.setSize(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { }

        })

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

        return binding.root
    }
}