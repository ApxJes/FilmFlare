package com.example.flimflare.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flimflare.R
import com.example.flimflare.adapter.TrendingTvShowAdapter
import com.example.flimflare.databinding.FragmentTvShowBinding
import com.example.flimflare.util.ConstantsURL.API_KEY
import com.example.flimflare.util.ConstantsURL.QUERY_PAGE_SIZE
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.TVShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class TvShowFragment : Fragment(R.layout.fragment_tv_show) {

    private lateinit var binding: FragmentTvShowBinding
    private val viewModel: TVShowViewModel by viewModels()
    @Inject
    lateinit var trendingTvShowAdapter: TrendingTvShowAdapter
    private val args: TvShowFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTvShowBinding.bind(view)
        setUpRecyclerView()
        getAllTvShow()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllTvShow() {
        val nation = when (args.language?.lowercase()) {
            "american" -> "en"
            "korea" -> "ko"
            else  -> "ja"
        }
        trendingOnTheWeek(nation)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun trendingOnTheWeek(nation: String) {
        viewModel.getTrendingOnTheWeekTvShow(API_KEY, nation)

        viewModel.trendingOnThisWeekTvShow.observe(viewLifecycleOwner) { resultResponse ->
            when (resultResponse) {
                is Resource.Success -> {
                    hidePrg()
                    resultResponse.data?.let { response ->
                        val filteredShows = response.results.filter { it.original_language == nation }
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

    private fun setUpRecyclerView() {
        binding.rvTrendingTvShow.apply {
            adapter = trendingTvShowAdapter
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
