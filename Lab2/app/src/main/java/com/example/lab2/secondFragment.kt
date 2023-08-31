package com.example.lab2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lab2.databinding.FragmentSecondBinding

class secondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentSecondBinding.inflate(inflater)

        val viewModel : myViewModel by activityViewModels()
        viewModel.color.observe(viewLifecycleOwner){
            binding.customView
        }
        return binding.root

    }


}