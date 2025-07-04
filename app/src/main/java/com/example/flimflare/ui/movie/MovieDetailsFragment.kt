package com.example.flimflare.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.flimflare.R
import com.example.flimflare.adapter.credit.CastAdapter
import com.example.flimflare.adapter.credit.CrewAdapter
import com.example.flimflare.databinding.FragmentMovieDetailsBinding
import com.example.flimflare.model.credits.Crew
import com.example.flimflare.model.room.MovieEntity
import com.example.flimflare.util.ConstantsURL.IMAGE_URL
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.movie.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject lateinit var castAdapter: CastAdapter
    @Inject lateinit var crewAdapter: CrewAdapter

    private lateinit var movieEntity: MovieEntity

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
        val result = args.result
        setupDetails(result!!.id)
        getCredits(result.id)

        toPersonDetails()
    }
 
    @SuppressLint("SetTextI18n")
    private fun setupDetails(id: Int) {
        viewModel.getMovieDetails(id)

        viewModel.movieDetailsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { result ->
                        binding.txvDetailsTitle.text = result.title
                        binding.txvDetailsReleaseDate.text = "Release date: ${result.release_date}"
                        binding.txvDetailsRunTime.text = "Run time: ${result.runtime} min"
                        binding.txvOverView.text = result.overview
                        binding.txvDetailsRating.text = "IMBD: ${result.vote_average}"

                        view?.let {
                            Glide.with(it)
                                .load(IMAGE_URL + result.poster_path)
                                .into(binding.imvDetailsPoster)
                        }

                        view?.let {
                            Glide.with(it)
                                .load(IMAGE_URL + result.poster_path)
                                .into(binding.imvRealPoster)
                        }

                        val genres = result.genres.map {
                            it.name
                        }

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

                        movieEntity = MovieEntity(
                            id = result.id,
                            movieTitle = result.title,
                            moviePoster = result.poster_path,
                            movieResult = args.result
                        )

                        setSaveMovie()
                        binding.imvSave.setOnClickListener {
                            isSaveMovie = if (!isSaveMovie) {
                                viewModel.insertMovie(movieEntity)
                                binding.imvSave.setImageResource(R.drawable.ic_red_heart)
                                view?.let { v ->
                                    Snackbar.make(
                                        v,
                                        "Successfully saved",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                                true

                            } else {
                                viewModel.deleteMovie(movieEntity)
                                binding.imvSave.setImageResource(R.drawable.ic_save)
                                view?.let { v ->
                                    Snackbar.make(
                                        v,
                                        "Unsaved",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                                false
                            }
                        }
                    }
                }

                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private var isSaveMovie = false

    private fun setSaveMovie() {
        viewModel.getAllMovie()
        viewModel.saveMovieList.observe(viewLifecycleOwner) { entity ->
            for (i in entity.indices) {
                if (args.result?.id == entity[i].id) {
                    binding.imvSave.setImageResource(R.drawable.ic_red_heart)
                    isSaveMovie = true
                    break
                } else {
                    isSaveMovie = false
                }
            }
        }
    }


    private fun getCredits(movieId: Int) {

        viewModel.getCredits(movieId)
        viewModel.creditsResponse.observe(viewLifecycleOwner) {result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let { resultResponse ->
                        val director = resultResponse.crew.firstOrNull {
                            it.job ==  "Director"
                        }
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

            binding.imvDirectorPoster.setOnClickListener {
                val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPersonDetailsFragment(director.id)
                findNavController().navigate(action)
            }

        } else {
            binding.txvDirectorName.text = ""
        }
    }

    private fun toPersonDetails() {
        castAdapter.onClickListener {
            val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPersonDetailsFragment(it)
            findNavController().navigate(action)
        }

        crewAdapter.onClickListener {
            val action =
                MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPersonDetailsFragment(it)
            findNavController().navigate(action)
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