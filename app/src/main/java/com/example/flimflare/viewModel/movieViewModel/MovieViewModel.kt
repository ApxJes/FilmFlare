package com.example.flimflare.viewModel.movieViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flimflare.model.nowPlaying.NowPlayingResponse
import com.example.flimflare.model.popular.PopularResponse
import com.example.flimflare.model.topRate.TopRateResponse
import com.example.flimflare.model.upcoming.UpcomingResponse
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

    private val _popularResponse: MutableLiveData<Resource<PopularResponse>> = MutableLiveData()
    val popularResponse: LiveData<Resource<PopularResponse>> get() = _popularResponse
    var popularPage = 1

    private val _topRateResponse: MutableLiveData<Resource<TopRateResponse>> = MutableLiveData()
    val topRateResponse: LiveData<Resource<TopRateResponse>> get() = _topRateResponse
    var topRatePage = 1

    private val _upcomingResponse: MutableLiveData<Resource<UpcomingResponse>> = MutableLiveData()
    val upcomingResponse: LiveData<Resource<UpcomingResponse>> get() = _upcomingResponse
    var upcomingPage = 1

    fun getNowPlayingMovie() = viewModelScope.launch {
        _nowPlayingResponse.postValue(Resource.Loading())
        val response = repository.getNowPlaying(apiKey = API_KEY, pageNumber = nowPlayingPage)
        _nowPlayingResponse.postValue(handleNowPlaying(response))
    }

    fun getPopularMovie() = viewModelScope.launch {
        _popularResponse.postValue(Resource.Loading())
        val response = repository.getPopular(apiKey = API_KEY, pageNumber = popularPage)
        _popularResponse.postValue(handlePopular(response))
    }

    fun getTopRateMovie() = viewModelScope.launch {
        _topRateResponse.postValue(Resource.Loading())
        val response = repository.getTopRate(apiKey = API_KEY, pageNumber = topRatePage)
        _topRateResponse.postValue(handleTopRate(response))
    }

    fun getUpcomingMovie() = viewModelScope.launch {
        _upcomingResponse.postValue(Resource.Loading())
        val response = repository.getUpcoming(apiKey = API_KEY, pageNumber = topRatePage)
        _upcomingResponse.postValue(handleUpcoming(response))
    }

    private fun handleNowPlaying(response: Response<NowPlayingResponse>): Resource<NowPlayingResponse> {
        if(response.isSuccessful) {
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handlePopular(response: Response<PopularResponse>): Resource<PopularResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handleTopRate(response: Response<TopRateResponse>): Resource<TopRateResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handleUpcoming(response: Response<UpcomingResponse>): Resource<UpcomingResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }
}