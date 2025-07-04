package com.example.flimflare.ui.tvShow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.adapter.tvShow.SearchTvShowAdapter
import com.example.flimflare.databinding.FragmentSearchTvShowBinding
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.tvShow.TVShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchTvShowFragment : Fragment() {

    private var _binding: FragmentSearchTvShowBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var searchAdapter: SearchTvShowAdapter
    private val viewModel: TVShowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcvForSearch()

        binding.imvBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        searchAdapter.onItemClick {
            val action = SearchTvShowFragmentDirections.actionSearchTvShowFragmentToTvShowDetailsFragment(it)
            findNavController().navigate(action)
        }

        var job: Job? = null
        binding.edtSearchTvShow.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(300L)
                editable?.let {
                    if(it.toString().isNotEmpty()) {
                        viewModel.getSearchTvShow(it.toString())
                    }
                }
            }
        }

        viewModel.searchTvShow.observe(viewLifecycleOwner) { result ->

            when(result) {
                is Resource.Success -> {
                    hideProgressBar()
                    result.data?.let { response ->
                        searchAdapter.differ.submitList(response.results)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "An error occur ${result.message}", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun rcvForSearch() {
        binding.rcvSearch.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        binding.prgBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.prgBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}