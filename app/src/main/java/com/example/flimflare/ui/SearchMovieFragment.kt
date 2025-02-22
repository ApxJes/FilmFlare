package com.example.flimflare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.SearchAdapter
import com.example.flimflare.databinding.FragmentSearchMovieBinding
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.movieViewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchMovieFragment : Fragment(R.layout.fragment_search_movie) {

    private var _binding: FragmentSearchMovieBinding? = null
    private val binding: FragmentSearchMovieBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    @Inject lateinit var searchAdapter: SearchAdapter

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

        searchAdapter.onItemClick {
            val action = SearchMovieFragmentDirections.actionSearchMovieFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        }

        var job: Job? = null
        binding.edtSearchMovie.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(300L)
                it?.let {
                    if(it.toString().isNotEmpty()) {
                        viewModel.getSearchMovie(it.toString())
                    }
                }
            }
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