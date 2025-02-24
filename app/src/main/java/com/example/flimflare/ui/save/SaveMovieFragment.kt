package com.example.flimflare.ui.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flimflare.R
import com.example.flimflare.databinding.FragmentSaveMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveMovieFragment : Fragment() {

    private var _binding: FragmentSaveMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveMovieBinding.inflate(inflater, container, false)
        return binding.root
    }
}