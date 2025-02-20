package com.example.flimflare.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.flimflare.R
import com.example.flimflare.databinding.FragmentTvShowDetailsBinding
import com.example.flimflare.util.ConstantsURL.IMAGE_URL
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.TVShowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailsFragment : Fragment(R.layout.fragment_tv_show_details) {

    private lateinit var binding: FragmentTvShowDetailsBinding
    private val viewModel: TVShowViewModel by viewModels()
    val args: TvShowDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTvShowDetailsBinding.bind(view)

        val id = args.showId
        getDetails(id)
    }

    @SuppressLint("SetTextI18n")
    private fun getDetails(showId: Int) {

        viewModel.getTvShowDetails(showId)
        viewModel.tvShowDetails.observe(viewLifecycleOwner){result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let { response ->
                        binding.txvShowTitle.text = response.name
                        binding.txvFirstAirDate.text = response.first_air_date
                        binding.txvLastAirDate.text = response.last_air_date
                        binding.txvShowOverView.text = response.overview
                        binding.txvNumberOfSeason.text = response.number_of_seasons.toString()
                        binding.txvNumberOfEpisodes.text = response.number_of_episodes.toString()
                        view?.let { Glide.with(it).load(IMAGE_URL + response.poster_path ).into(binding.imvShowPoster)}
                        binding.txvShowType.text = response.genres.joinToString(",") { it.name }
                    }
                }

                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }
}