package com.example.flimflare.ui.tvShow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.flimflare.R
import com.example.flimflare.adapter.tvShow.SeasonAdapter
import com.example.flimflare.adapter.tvShow.ShowCreatorAdapter
import com.example.flimflare.databinding.FragmentTvShowDetailsBinding
import com.example.flimflare.model.room.TvShowEntity
import com.example.flimflare.util.ConstantsURL.IMAGE_URL
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.tvShow.TVShowViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowDetailsFragment : Fragment(R.layout.fragment_tv_show_details) {

    private lateinit var binding: FragmentTvShowDetailsBinding
    private val viewModel: TVShowViewModel by viewModels()
    private val args: TvShowDetailsFragmentArgs by navArgs()
    private lateinit var saveTvShowEntity: TvShowEntity

    @Inject lateinit var showCreatorAdapter: ShowCreatorAdapter
    @Inject lateinit var seasonAdapter: SeasonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTvShowDetailsBinding.bind(view)

        seasonAdapter.onClickListener {
            goToSeasonDetails(args.tvShow!!.id, it)
        }
        val result = args.tvShow
        getDetails(result!!.id)
    }

    private fun goToSeasonDetails(seriesId: Int, seasonNumber: Int) {
        val action = TvShowDetailsFragmentDirections.actionTvShowDetailsFragmentToEachSeasonDetailsFragment(seriesId, seasonNumber)
        findNavController().navigate(action)
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

                        rcvForShowCreator()
                        showCreatorAdapter.differ.submitList(response.created_by)

                        rcvForSeason()
                        seasonAdapter.differ.submitList(response.seasons)

                        setUpSaveTvShow()

                        saveTvShowEntity = TvShowEntity(
                            id = response.id,
                            showTitle = response.name,
                            showPoster = response.poster_path,
                            showResult = args.tvShow
                        )

                        binding.imvSave.setOnClickListener {
                            isSaveTvShow = if (!isSaveTvShow) {
                                viewModel.saveTvShow(saveTvShowEntity)
                                binding.imvSave.setImageResource(R.drawable.ic_red_heart)
                                view?.let {
                                    Snackbar.make(it, "Successfully saved", Snackbar.LENGTH_SHORT).show()
                                }

                                true

                            } else {
                                viewModel.deleteTvShow(saveTvShowEntity)
                                view?.let {
                                    Snackbar.make(it, "Unsaved", Snackbar.LENGTH_SHORT).show()
                                }
                                false
                            }
                        }

                    }
                }

                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private var isSaveTvShow = false
    private fun setUpSaveTvShow() {
        viewModel.getAllTvShow()

        viewModel.saveTvShowList.observe(viewLifecycleOwner) { entities ->
            for (i in entities.indices) {
                if (args.tvShow?.id == entities[i].id) {
                    isSaveTvShow = true
                    binding.imvSave.setImageResource(R.drawable.ic_red_heart)
                    break

                } else {
                    isSaveTvShow = false
                }
            }
        }
    }

    private fun rcvForShowCreator() {
        binding.rvShowCreator.apply {
            adapter = showCreatorAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun rcvForSeason() {
        binding.rvShowSeason.apply {
            adapter = seasonAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}