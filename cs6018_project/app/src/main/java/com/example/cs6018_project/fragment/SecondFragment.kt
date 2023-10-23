package com.example.cs6018_project.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cs6018_project.DynamicConfig
import com.example.cs6018_project.R
import com.example.cs6018_project.databinding.FragmentSecondBinding
import com.example.cs6018_project.mvvm.BoardViewModel
import kotlinx.coroutines.CoroutineScope
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class SecondFragment : Fragment() {

    lateinit var viewModel : BoardViewModel

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

        fun setColor(color: Int) {
            binding.viewModel?.setColor(color)
        }

        binding.buttonBlueColor.setOnClickListener { setColor(Color.BLUE) }
        binding.buttonRedColor.setOnClickListener { setColor(Color.RED) }
        binding.buttonBlackColor.setOnClickListener { setColor(Color.BLACK) }
        binding.buttonGreenColor.setOnClickListener { setColor(Color.GREEN) }
        binding.buttonYellowColor.setOnClickListener { setColor(Color.YELLOW) }
        binding.buttonCyanColor.setOnClickListener { setColor(Color.CYAN) }
        binding.buttonGaryColor.setOnClickListener { setColor(Color.GRAY) }

        binding.buttonCircle.setOnClickListener {
            binding.viewModel?.setShape(PenShape.circle)
            binding.buttonCircle.text = getString(R.string.brush_round)
            binding.buttonSquare.text = "□"
        }

        binding.buttonSquare.setOnClickListener {
            binding.viewModel?.setShape(PenShape.square)
            binding.buttonCircle.text = "◯"
            binding.buttonSquare.text = getString(R.string.brush_square)
        }

        binding.buttonShare.setOnClickListener {
            val file = File(requireContext().cacheDir, "test" + ".png")
            val fOut = FileOutputStream(file)
            val bitmap = viewModel.getCurrentBitmap()
            Log.wtf("*", bitmap.toString())
            bitmap.compress(CompressFormat.PNG, 95, fOut)
            fOut.flush()
            fOut.close()

            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(requireContext(),
                (context?.packageName?: "") + ".provider", file))
            intent.type = "image/*"
            startActivity(intent)
        }

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

    class PenShape {
        companion object {
            const val circle: Int = 1
            const val square: Int = 2
        }
    }
}