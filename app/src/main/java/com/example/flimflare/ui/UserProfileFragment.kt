package com.example.flimflare.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.flimflare.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var selectedPhotoUri: Uri? = null
    var photoUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setupListeners()

        checkLogInState()
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            profileUpdate()
        }

        binding.txvUploadProfile.setOnClickListener {
            uploadProfilePhoto()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun profileUpdate() {
        val name = binding.edtName.text.toString()
        binding.txvName.text = ""


        val user = auth.currentUser

        if (user != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .also { requestBuilder ->
                    selectedPhotoUri?.let {
                        requestBuilder.photoUri = it
                    }
                }
                .build()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    user.updateProfile(profileUpdates).await()
                    withContext(Dispatchers.Main) {
                        checkLogInState()
                        Toast.makeText(
                            requireContext(),
                            "Successfully update the profile",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun uploadProfilePhoto() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == android.app.Activity.RESULT_OK) {
            selectedPhotoUri = data?.data
            binding.profile.setImageURI(selectedPhotoUri)
        }
    }

    private fun checkLogInState() {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(requireContext(), "You are not logged in", Toast.LENGTH_SHORT).show()
        } else {
            binding.txvName.text = user.displayName
            binding.edtName.setText(user.displayName)

            photoUri = user.photoUrl
            if (photoUri != null){
                Glide.with(this).load(photoUri).into(binding.profile)
            } else {
                binding.profile.setImageResource(android.R.drawable.ic_menu_camera)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
