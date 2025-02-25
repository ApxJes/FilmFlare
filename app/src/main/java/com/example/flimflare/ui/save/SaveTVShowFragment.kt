package com.example.flimflare.ui.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.save.SaveTvShowAdapter
import com.example.flimflare.databinding.FragmentSaveTVShowBinding
import com.example.flimflare.viewModel.tvShow.TVShowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveTVShowFragment : Fragment() {

    private var _binding: FragmentSaveTVShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var saveTvShowAdapter: SaveTvShowAdapter
    private val viewModel: TVShowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveTVShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rcvForSaveShow.apply {
            layoutManager = GridLayoutManager(activity, 2)
            setHasFixedSize(true)
        }

        viewModel.getAllTvShow()
        viewModel.saveTvShowList.observe(viewLifecycleOwner) {
            saveTvShowAdapter = SaveTvShowAdapter(it)
            binding.rcvForSaveShow.adapter = saveTvShowAdapter
            saveTvShowAdapter.notifyDataSetChanged()
        }
    }
}