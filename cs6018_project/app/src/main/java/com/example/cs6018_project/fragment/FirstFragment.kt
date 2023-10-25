package com.example.cs6018_project.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cs6018_project.DynamicConfig
import com.example.cs6018_project.R
import com.example.cs6018_project.databinding.FragmentFirstBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentFirstBinding.inflate(inflater, container, false)
        val navController = findNavController()

        binding.bt1.setOnClickListener {

            navController.navigate(R.id.action_firstFragment_to_savedBoardFragment)
        }

        if (!DynamicConfig.flagSplashShowed) {
            navController.navigate(R.id.action_firstFragment_to_splashFragment)
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

                activity?.finishAndRemoveTask()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}