package com.example.flimflare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.SeasonDetailsAdapter
import com.example.flimflare.databinding.FragmentEachSeasonDetailsBinding
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.TVShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EachSeasonDetailsFragment : Fragment(R.layout.fragment_each_season_details) {

    private lateinit var binding: FragmentEachSeasonDetailsBinding
    private val viewModel: TVShowViewModel by viewModels()
    val args: EachSeasonDetailsFragmentArgs by navArgs()

    @Inject lateinit var seasonDetailsAdapter: SeasonDetailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEachSeasonDetailsBinding.bind(view)
        rcvForEachSeason()

        val seriesId = args.seriedId
        val seasonNumber = args.seasonNumber

        eachSeasonDetails(seriesId, seasonNumber)
    }

    private fun eachSeasonDetails(seriesId: Int, seasonNumber: Int) {

        viewModel.getEachSeasonDetails(seriesId, seasonNumber)

        viewModel.eachSeasonDetails.observe(viewLifecycleOwner) {result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let { response ->
                        seasonDetailsAdapter.differ.submitList(response.episodes)
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), "An error occur", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {}
            }
        }
    }

    private fun rcvForEachSeason() {
        binding.rcvForEpisode.apply {
            adapter = seasonDetailsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }

}