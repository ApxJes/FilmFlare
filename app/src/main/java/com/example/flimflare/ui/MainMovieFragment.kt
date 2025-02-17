package com.example.flimflare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.NowPlayingAdapter
import com.example.flimflare.adapter.PopularAdapter
import com.example.flimflare.databinding.FragmentMainMovieBinding
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.movieViewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainMovieFragment : Fragment() {

    private var _binding: FragmentMainMovieBinding? = null
    private val binding: FragmentMainMovieBinding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()

    @Inject lateinit var nowPlayingAdapter: NowPlayingAdapter
    @Inject lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNowPlayingMovie()
        getPopularMovie()

        viewModel.getNowPlayingMovie()
        viewModel.getPopularMovie()
    }

    private fun getNowPlayingMovie() {
        nowPlayingRecyclerView()

        viewModel.nowPlayingResponse.observe(viewLifecycleOwner){ result ->
            when(result) {
                is Resource.Success -> {
                    hidePrg()
                    result.data?.let { nowPlayingResponse ->
                        nowPlayingAdapter.differ.submitList(nowPlayingResponse.results)
                    }
                }

                is Resource.Error -> {
                    hidePrg()
                    Toast.makeText(requireContext(), "An error occur ${result.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {showPrg()}
            }
        }
    }

    private fun getPopularMovie() {
        popularRecyclerView()

        viewModel.popularResponse.observe(viewLifecycleOwner) {result ->
            when(result) {
                is Resource.Success -> {
                    hidePrg()
                    result.data?.let { resultResponse ->
                        popularAdapter.differ.submitList(resultResponse.results)
                    }
                }
                is Resource.Error -> {
                    hidePrg()
                    Toast.makeText(requireContext(), "An error occur ${result.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {showPrg()}
            }
        }
    }

    private fun nowPlayingRecyclerView() {
        binding.rvNowPlayingMovie.apply {
            adapter = nowPlayingAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun popularRecyclerView() {
        binding.rvPopularMovie.apply {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showPrg(){
        binding.prgBar.visibility = View.VISIBLE
    }

    private fun hidePrg() {
        binding.prgBar.visibility = View.INVISIBLE
    }
}