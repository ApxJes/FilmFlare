package com.example.flimflare.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.flimflare.R
import com.example.flimflare.databinding.FragmentMovieDetailsBinding
import com.example.flimflare.util.ConstantsURL.IMAGE_URL
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.movieViewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nowPlayingId = args.nowPlayingId
        setupDetailsForNowPlaying(nowPlayingId)
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetailsForNowPlaying(nowPlayingId: Int) {
        viewModel.getMovieDetails(nowPlayingId)

        viewModel.movieDetailsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { result ->
                        binding.txvDetailsTitle.text = result.title
                        binding.txvDetailsReleaseDate.text = result.release_date
                        binding.txvDetailsRunTime.text = result.runtime.toString() + "min"
                        val genresText = result.genres.joinToString(",") {it.name }
                        binding.txvType1.text = genresText
                        view?.let {
                            Glide.with(it)
                                .load(IMAGE_URL + result.poster_path)
                                .into(binding.imvDetailsPoster)
                        }

                        val genres = result.genres.map { it.name }
                        if(genres.isNotEmpty()){
                            binding.txvType1.text = genres[0]
                            binding.txvType1.visibility = View.VISIBLE
                        } else {
                            binding.txvType1.visibility = View.GONE
                        }

                        if (genres.size > 1) {
                            binding.txvType2.text = genres[1]
                            binding.txvType2.visibility = View.VISIBLE
                        } else {
                            binding.txvType1.visibility = View.GONE
                        }
                    }
                }

                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }
}