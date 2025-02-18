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
import com.example.flimflare.databinding.FragmentPersonDetailsBinding
import com.example.flimflare.util.ConstantsURL.IMAGE_URL
import com.example.flimflare.util.Resource
import com.example.flimflare.viewModel.movieViewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailsFragment : Fragment() {

    private var _binding: FragmentPersonDetailsBinding? = null
    private val binding: FragmentPersonDetailsBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    private val args: PersonDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personId = args.personId
        castDetails(personId)
    }

    @SuppressLint("SetTextI18n")
    private fun castDetails(personId: Int) {

        viewModel.getPersonDetails(personId)

        viewModel.personDetailResponse.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let { resultResponse ->
                        binding.txvPersonName.text = "Name: ${resultResponse.name}"
                        binding.txvDetailsBirthDate.text = "Birth date: ${resultResponse.birthday}"
                        binding.txvGender.text = if(resultResponse.gender == 0)"Gender: Female" else "Gender: Male"
                        binding.txvPlace.text = resultResponse.place_of_birth
                        binding.txvBio.text = resultResponse.biography
                        binding.txvDetailsJob.text = resultResponse.known_for_department

                        view?.let {
                            Glide.with(it).load(IMAGE_URL + resultResponse.profile_path).into(binding.imvPersonProfile)
                        }
                    }
                }

                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }
}