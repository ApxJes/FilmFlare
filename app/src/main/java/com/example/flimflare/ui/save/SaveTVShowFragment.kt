package com.example.flimflare.ui.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flimflare.R
import com.example.flimflare.databinding.FragmentSaveTVShowBinding

class SaveTVShowFragment : Fragment() {

    private var _binding: FragmentSaveTVShowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveTVShowBinding.inflate(inflater, container, false)
        return binding.root
    }
}