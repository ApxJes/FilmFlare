package com.example.flimflare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flimflare.R
import com.example.flimflare.databinding.FragmentMainTvShowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainTvShowFragment : Fragment(R.layout.fragment_main_tv_show) {
    private lateinit var binding: FragmentMainTvShowBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainTvShowBinding.bind(view)

        binding.imvAmerican.setOnClickListener {
            val action = MainTvShowFragmentDirections.actionMainTvShowFragmentToAmericanTvShowFragment()
            findNavController().navigate(action)
        }

        binding.imvKoreaDrama.setOnClickListener {
            val action = MainTvShowFragmentDirections.actionMainTvShowFragmentToKdramaTvShowFragment()
            findNavController().navigate(action)
        }
    }
}