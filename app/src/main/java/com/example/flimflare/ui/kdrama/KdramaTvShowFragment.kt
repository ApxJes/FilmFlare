package com.example.flimflare.ui.kdrama

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.TrendingTvShowAdapter
import com.example.flimflare.databinding.FragmentKdramaTvShowBinding
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.KdramaViweModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KdramaTvShowFragment : Fragment(R.layout.fragment_kdrama_tv_show) {

    private lateinit var binding: FragmentKdramaTvShowBinding
    @Inject
    lateinit var trendingTvShowAdapter: TrendingTvShowAdapter
    private val viewModel: KdramaViweModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKdramaTvShowBinding.bind(view)

        rcvForTrendingKdrama()
        observeTrendingKdrama()

        viewModel.getTrendingKdrama("kr")
    }

    private fun observeTrendingKdrama() {
        viewModel.trendingKdramaResponse.observe(viewLifecycleOwner) {resultResponse ->
            when(resultResponse) {
                is Resource.Success -> {
                    resultResponse.data?.let { result ->
                        trendingTvShowAdapter.differ.submitList(result.results)
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), "An error occur ${resultResponse.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {}
            }
        }
    }

    private fun rcvForTrendingKdrama() {
        binding.rvTrendingKdrama.apply {
            adapter = trendingTvShowAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}