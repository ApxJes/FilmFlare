package com.example.flimflare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.CastAdapter
import com.example.flimflare.adapter.CrewAdapter
import com.example.flimflare.adapter.SeasonDetailsAdapter
import com.example.flimflare.databinding.FragmentEachSeasonDetailsBinding
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.tvShow.TVShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EachSeasonDetailsFragment : Fragment(R.layout.fragment_each_season_details) {

    private lateinit var binding: FragmentEachSeasonDetailsBinding
    private val viewModel: TVShowViewModel by viewModels()
    private val args: EachSeasonDetailsFragmentArgs by navArgs()

    @Inject lateinit var seasonDetailsAdapter: SeasonDetailsAdapter
    @Inject lateinit var castAdapter: CastAdapter
    @Inject lateinit var crewAdapter: CrewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEachSeasonDetailsBinding.bind(view)
        rcvForEachSeason()
        rcvForSeasonCast()
        rcvForSeasonCrew()

        val seriesId = args.seriedId
        val seasonNumber = args.seasonNumber

        eachSeasonDetails(seriesId, seasonNumber)
        getShowCredits(seriesId, seasonNumber)

        castAdapter.onClickListener {
            goToPersonDetails(it)
        }

        crewAdapter.onClickListener {
            goToPersonDetails(it)
        }
    }

    private fun goToPersonDetails(id: Int) {
        val action = EachSeasonDetailsFragmentDirections.actionEachSeasonDetailsFragmentToPersonDetailsFragment(id)
        findNavController().navigate(action)
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

    private fun getShowCredits(seriesId: Int, seasonNumber: Int) {
        viewModel.getShowCredits(seriesId, seasonNumber)

        viewModel.showCredits.observe(viewLifecycleOwner){result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { response ->
                        castAdapter.differ.submitList(response.cast)
                        crewAdapter.differ.submitList(response.crew)
                    }
                }

                is Resource.Error -> {}
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

    private fun rcvForSeasonCast() {
        binding.rcvForCast.apply {
            adapter = castAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun rcvForSeasonCrew() {
        binding.rcvForCrew.apply {
            adapter = crewAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}