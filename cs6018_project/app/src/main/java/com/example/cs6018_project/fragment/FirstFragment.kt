package com.example.cs6018_project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.cs6018_project.R
// import androidx.fragment.app.activityViewModels
import com.example.cs6018_project.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.bt1.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_firstFragment_to_savedBoardFragment)
        }

        return binding.root
    }

}