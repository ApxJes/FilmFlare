package com.example.flimflare.ui.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flimflare.R
import com.example.flimflare.adapter.save.SaveAdapter
import com.example.flimflare.databinding.FragmentSaveMovieBinding
import com.example.flimflare.viewModel.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SaveMovieFragment : Fragment() {

    private var _binding: FragmentSaveMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var saveAdapter: SaveAdapter
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveMovieBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rcvForSaveMovie.apply { 
            layoutManager = GridLayoutManager(activity, 2)
            setHasFixedSize(true)
        }

        viewModel.getAllMovie()
        viewModel.saveMovieList.observe(viewLifecycleOwner) {
            saveAdapter = SaveAdapter(it)
            binding.rcvForSaveMovie.adapter = saveAdapter
            saveAdapter.notifyDataSetChanged()
        }
    }
}