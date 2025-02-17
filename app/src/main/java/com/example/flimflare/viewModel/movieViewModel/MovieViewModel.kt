package com.example.flimflare.viewModel.movieViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flimflare.model.nowPlaying.NowPlayingResponse
import com.example.flimflare.repository.movieRepository.MovieRepository
import com.example.flimflare.util.ConstantsURL.API_KEY
import com.example.flimflare.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val repository: MovieRepository
): ViewModel(){

    private val _nowPlayingResponse: MutableLiveData<Resource<NowPlayingResponse>> = MutableLiveData()
    val nowPlayingResponse: LiveData<Resource<NowPlayingResponse>> get() = _nowPlayingResponse
    var nowPlayingPage = 1

    fun getNowPlayingMovie() = viewModelScope.launch {
        _nowPlayingResponse.postValue(Resource.Loading())
        val response = repository.getNowPlaying(apiKey = API_KEY, pageNumber = nowPlayingPage)
        _nowPlayingResponse.postValue(handleNowPlaying(response))
    }

    private fun handleNowPlaying(response: Response<NowPlayingResponse>): Resource<NowPlayingResponse> {
        if(response.isSuccessful) {
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }
}