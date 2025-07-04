package com.example.flimflare.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.adapter.movie.NowPlayingAdapter
import com.example.flimflare.adapter.movie.PopularAdapter
import com.example.flimflare.adapter.movie.TopRateAdapter
import com.example.flimflare.adapter.movie.UpcomingAdapter
import com.example.flimflare.databinding.FragmentMainMovieBinding
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainMovieFragment : Fragment() {

    private var _binding: FragmentMainMovieBinding? = null
    private val binding: FragmentMainMovieBinding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()

    @Inject lateinit var nowPlayingAdapter: NowPlayingAdapter
    @Inject lateinit var popularAdapter: PopularAdapter
    @Inject lateinit var topRateAdapter: TopRateAdapter
    @Inject lateinit var upcomingAdapter: UpcomingAdapter

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
        getTppRateMovie()
        getUpcomingMovie()
        searchMovie()


        navigationToDetailFragment()

        viewModel.getNowPlayingMovie()
        viewModel.getPopularMovie()
        viewModel.getTopRateMovie()
        viewModel.getUpcomingMovie()

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

    private fun getTppRateMovie() {
        topRateRecyclerView()

        viewModel.topRateResponse.observe(viewLifecycleOwner) {result ->
            when(result) {
                is Resource.Success -> {
                    hidePrg()
                    result.data?.let { resultResponse ->
                        topRateAdapter.differ.submitList(resultResponse.results)
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

    private fun getUpcomingMovie() {
        upcomingRecyclerView()

        viewModel.upcomingResponse.observe(viewLifecycleOwner){ result ->
            when(result) {
                is Resource.Success -> {
                    hidePrg()
                    result.data?.let { upcomigResponse ->
                        upcomingAdapter.differ.submitList(upcomigResponse.results)
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

    private fun searchMovie() {
        binding.search.setOnClickListener{
            val action = MainMovieFragmentDirections.actionMainMovieFragmentToSearchMovieFragment("")
            findNavController().navigate(action)
        }
    }

    private fun navigationToDetailFragment() {
        nowPlayingAdapter.onItemClick{
            val action = MainMovieFragmentDirections.actionMainMovieFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        }

        popularAdapter.onItemClick {
            val action = MainMovieFragmentDirections.actionMainMovieFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        }

        topRateAdapter.onItemClick {
            val action = MainMovieFragmentDirections.actionMainMovieFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        }

        upcomingAdapter.onItemClick {
            val action = MainMovieFragmentDirections.actionMainMovieFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
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

    private fun topRateRecyclerView() {
        binding.rvTopRateMovie.apply {
            adapter = topRateAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun upcomingRecyclerView() {
        binding.rvUpcoming.apply {
            adapter = upcomingAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showPrg(){
        binding.prgBar.visibility = View.VISIBLE
    }

    private fun hidePrg() {
        binding.prgBar.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}