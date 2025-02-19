package com.example.flimflare.ui.american

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.TrendingTvShowAdapter
import com.example.flimflare.databinding.FragmentAmericanTvShowBinding
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.TvShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AmericanTvShowFragment : Fragment(R.layout.fragment_american_tv_show) {

    private lateinit var binding: FragmentAmericanTvShowBinding

    @Inject
    lateinit var trendingTvShowAdapter: TrendingTvShowAdapter
    private val viewModel: TvShowViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAmericanTvShowBinding.bind(view)

        setupRecyclerView()
        observeTrendingTvShows()

        viewModel.getTrendingTvShow("en")
    }

    private fun observeTrendingTvShows() {
        viewModel.trendingTvShowResponse.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Success -> {
                    resources.data?.let { result ->
                        trendingTvShowAdapter.differ.submitList(result.results)
                    }
                }

                is Resource.Error -> {
                    Log.e("AmericanTvShowFragment", "Error: ${resources.message}")
                }

                is Resource.Loading -> {

                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTrendingTvShow.apply {
            adapter = trendingTvShowAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}
