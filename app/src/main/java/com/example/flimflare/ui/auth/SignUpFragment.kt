package com.example.flimflare.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flimflare.R
import com.example.flimflare.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        signUp()

        binding.btnLogIn.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLogInFragment()
            findNavController().navigate(action)
        }
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {

            val email = binding.edtSignUpEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                binding.edtSignUpEmail.error = "Email is require!"
                binding.edtSignUpEmail.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                binding.edtPassword.error = "Password is require!"
                binding.edtPassword.requestFocus()
            } else {
                setUpSignUp(email, password)
            }
        }
    }

    private fun setUpSignUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    "Account created! Please log in",
                    Toast.LENGTH_SHORT
                ).show()
                Handler(Looper.getMainLooper()).postDelayed(
                    { ->
                        findNavController().navigate(R.id.logInFragment)
                    }, 1000
                )

            } else {
                Toast.makeText(requireContext(), "Fail log sign up", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}