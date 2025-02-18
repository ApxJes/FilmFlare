package com.example.flimflare.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.flimflare.adapter.CastAdapter
import com.example.flimflare.adapter.CrewAdapter
import com.example.flimflare.databinding.FragmentMovieDetailsBinding
import com.example.flimflare.model.details.credits.Crew
import com.example.flimflare.util.ConstantsURL.IMAGE_URL
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.movieViewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    val args: MovieDetailsFragmentArgs by navArgs()

    @Inject lateinit var castAdapter: CastAdapter
    @Inject lateinit var crewAdapter: CrewAdapter

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
        val id = args.nowPlayingId
        setupDetails(id)
        getDirector(id)
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails(nowPlayingId: Int) {
        viewModel.getMovieDetails(nowPlayingId)

        viewModel.movieDetailsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { result ->
                        binding.txvDetailsTitle.text = result.title
                        binding.txvDetailsReleaseDate.text = "Release date: ${result.release_date}"
                        binding.txvDetailsRunTime.text = "Run time: ${result.runtime} min"
                        binding.txvOverView.text = result.overview
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

    private fun getDirector(movieId: Int) {

        viewModel.getCredits(movieId)
        viewModel.creditsResponse.observe(viewLifecycleOwner) {result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let { resultResponse ->
                        val director = resultResponse.crew.firstOrNull { it.job ==  "Director" }
                        movieDirector(director)
                        castAdapter.differ.submitList(resultResponse.cast)
                        recyclerViewForCast()

                        crewAdapter.differ.submitList(resultResponse.crew)
                        recyclerViewForCrew()
                    }
                }

                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun movieDirector(director: Crew?) {
        if (director != null) {
            binding.txvDirectorName.text = "Directed by - ${director.name}"
            view?.let {
                Glide.with(it)
                    .load(IMAGE_URL + director.profile_path)
                    .into(binding.imvDirectorPoster)
            }
        } else {
            binding.txvDirectorName.text = ""
        }
    }

    private fun recyclerViewForCast() {
        binding.rcvCast.apply {
            adapter = castAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun recyclerViewForCrew() {
        binding.rcvCrew.apply {
            adapter = crewAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}