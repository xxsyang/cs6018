package com.example.lab2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lab2.databinding.FragmentNavigatesBinding
class navigatesFragment : Fragment() {
    private var buttonFunction : () -> Unit = {}

    fun setButtonFunction(newFunc: () -> Unit) {
        buttonFunction = newFunc
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentNavigatesBinding.inflate(inflater, container, false)
        val viewModel : myViewModel by activityViewModels()

        binding.bt1.setOnClickListener {
            viewModel.pickColor()
            buttonFunction()
        }

        return binding.root
    }
}