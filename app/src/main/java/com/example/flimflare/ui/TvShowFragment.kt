package com.example.flimflare.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.OnTheAirAdapter
import com.example.flimflare.adapter.TopRatingTvShowAdapter
import com.example.flimflare.adapter.TrendingTvShowAdapter
import com.example.flimflare.databinding.FragmentTvShowBinding
import com.example.flimflare.util.ConstantsURL.API_KEY
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.TVShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowFragment : Fragment(R.layout.fragment_tv_show) {

    private lateinit var binding: FragmentTvShowBinding
    private val viewModel: TVShowViewModel by viewModels()
    @Inject lateinit var trendingTvShowAdapter: TrendingTvShowAdapter
    @Inject lateinit var onTheAirAdapter: OnTheAirAdapter
    @Inject lateinit var topRatingTvShowAdapter: TopRatingTvShowAdapter

    private val args: TvShowFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTvShowBinding.bind(view)

        trendingTvShowAdapter.onClickListener {
            goToDetails(it)
        }

        rcvTrending()
        rcvOnTheAir()
        rcvTopRate()

        getAllTvShow()
    }

    private fun goToDetails(id: Int) {
        val action = TvShowFragmentDirections.actionTvShowFragmentToTvShowDetailsFragment(id)
        findNavController().navigate(action)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllTvShow() {
        val nation = when (args.language?.lowercase()) {
            "american" -> "US"
            "korea" -> "KR"
            else  -> "JP"
        }
        trendingOnTheWeek(nation)
        onAirToday(nation)
        topRateing(nation)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun trendingOnTheWeek(nation: String) {
        viewModel.getTrendingOnTheWeekTvShow(API_KEY, nation)

        viewModel.trendingOnThisWeekTvShow.observe(viewLifecycleOwner) { resultResponse ->
            when (resultResponse) {
                is Resource.Success -> {
                    hidePrg()
                    resultResponse.data?.let { response ->
                        val filteredShows = response.results.filter { it.origin_country.contains(nation) }
                        trendingTvShowAdapter.differ.submitList(filteredShows)
                    }
                }

                is Resource.Error -> {
                    hidePrg()
                    Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    showPrg()
                }
            }
        }
    }

    private fun onAirToday(nation: String) {

        viewModel.getOnTheAirTvShow(API_KEY, nation)

        viewModel.onTheAirTvShow.observe(viewLifecycleOwner){result ->
            when(result) {
                is Resource.Success -> {
                    hidePrg()
                    result.data?.let { resultResponse ->
                        val filterShow = resultResponse.results.filter { it.origin_country.contains(nation) }
                        onTheAirAdapter.differ.submitList(filterShow)
                    }
                }

                is Resource.Error -> {
                    hidePrg()
                    Toast.makeText(requireContext(), "An error occur", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {showPrg()}
            }
        }
    }

    private fun topRateing(nation: String) {
        viewModel.getTopRateTvShow(API_KEY, nation)

        viewModel.topRateTvShow.observe(viewLifecycleOwner){result ->
            when(result) {
                is Resource.Success -> {
                    hidePrg()
                    result.data?.let { resultResposne ->
                        val showFilter = resultResposne.results.filter { it.origin_country.contains(nation)}
                        topRatingTvShowAdapter.differ.submitList(showFilter)
                    }
                }

                is Resource.Error -> {
                    hidePrg()
                    Toast.makeText(requireContext(), "An error occur", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {showPrg()}
            }
        }
    }

    private fun rcvTrending() {
        binding.rvTrendingTvShow.apply {
            adapter = trendingTvShowAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun rcvOnTheAir() {
        binding.rvOnAirToday.apply {
            adapter = onTheAirAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun rcvTopRate() {
        binding.rvTopRateTvShow.apply {
            adapter = topRatingTvShowAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showPrg() {
        binding.prgBar.visibility = View.VISIBLE
    }

    private fun hidePrg() {
        binding.prgBar.visibility = View.GONE
    }
}
