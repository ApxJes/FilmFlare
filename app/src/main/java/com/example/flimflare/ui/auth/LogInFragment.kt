package com.example.flimflare.ui.auth

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.flimflare.R
import com.example.flimflare.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        logIn()

        binding.btnSignUp.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }

    private fun logIn() {
        binding.btnLogIn.setOnClickListener {
            val email = binding.edtLogInEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                binding.edtLogInEmail.error = "Email is require!"
                binding.edtLogInEmail.requestFocus()
            } else if(TextUtils.isEmpty(password)){
                binding.edtPassword.error = "Password is require!"
                binding.edtPassword.requestFocus()
            } else {
                setUpLogIn(email, password)
            }
        }
    }

    private fun setUpLogIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Toast.makeText(requireContext(), "Log in successful", Toast.LENGTH_SHORT).show()
                val action = LogInFragmentDirections.actionLogInFragmentToMainMovieFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Don't have an account", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}