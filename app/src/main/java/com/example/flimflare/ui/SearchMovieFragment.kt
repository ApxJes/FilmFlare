package com.example.flimflare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.SearchAdapter
import com.example.flimflare.databinding.FragmentPersonDetailsBinding
import com.example.flimflare.databinding.FragmentSearchMovieBinding
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.movieViewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import javax.inject.Inject

@AndroidEntryPoint
class SearchMovieFragment : Fragment(R.layout.fragment_search_movie) {

    private var _binding: FragmentSearchMovieBinding? = null
    private val binding: FragmentSearchMovieBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    @Inject lateinit var searchAdapter: SearchAdapter
    private val args: SearchMovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSearchRcv()

        val query = args.query
        query.let {
            viewModel.getSearchMovie(query)
        }

        viewModel.searchResponse.observe(viewLifecycleOwner) {result ->
            when(result) {
                is Resource.Success -> {
                    hidePrg()
                    result.data?.let { resultResponse ->
                        searchAdapter.differ.submitList(resultResponse.results)
                    }
                }

                is Resource.Error -> {hidePrg()}
                is Resource.Loading -> {showPrg()}
            }
        }
    }

    private fun setUpSearchRcv() {
        binding.rcvSearch.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showPrg() {
        binding.prgBar.visibility = View.VISIBLE
    }

    private fun hidePrg() {
        binding.prgBar.visibility = View.GONE
    }
}