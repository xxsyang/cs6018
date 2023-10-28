package com.example.cs6018_project.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cs6018_project.DynamicConfig
import com.example.cs6018_project.R
import com.example.cs6018_project.databinding.FragmentFirstBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.io.File

class FirstFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

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


        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // TODO (placeholder)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        binding.buttonLogin.setOnClickListener {
            signInGoogle()
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

    private fun signInGoogle() {
        Log.wtf("login", "signInGoogle")
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.wtf("login", "launcher result ok")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        } else {
            Log.wtf("login", "launcher result NOT ok")
            Log.wtf("login", "launcher result: " + result.resultCode)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        Log.wtf("login", "handleResults")
        if (task.isSuccessful) {
            val account: GoogleSignInAccount = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        Log.wtf("login", "updateUI")
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.wtf("login", "login successful")
                account.email // csproject524@gmail.com
                account.displayName // password: Abcde-12345 Name: Test
                Log.wtf("login", account.email)
                // TODO: bring the information to save_board_fragment
            } else {
                Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}