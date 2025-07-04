package com.example.flimflare.ui.tvShow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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
            tvShows("american")
        }

        binding.imvKoreaDrama.setOnClickListener {
            tvShows("korea")
        }

        binding.imvThaiDrama.setOnClickListener {
            tvShows("thailand")
        }

        binding.layoutSearchTvShow.setOnClickListener {
            val action = MainTvShowFragmentDirections.actionMainTvShowFragmentToSearchTvShowFragment("")
            findNavController().navigate(action)
        }
    }

    private fun tvShows(nation: String) {
        val action = MainTvShowFragmentDirections.actionMainTvShowFragmentToTvShowFragment(nation)
        findNavController().navigate(action)
    }
}