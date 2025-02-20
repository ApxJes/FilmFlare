package com.example.flimflare.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flimflare.model.showDetails.TvShowDetailsResponse
import com.example.flimflare.model.tvShow.onTheAir.OnTheAirResponse
import com.example.flimflare.model.tvShow.topRate.TopRateTvShowResponse
import com.example.flimflare.model.tvShow.trending.TrendingTvShowResponse
import com.example.flimflare.repository.TvShowRepository
import com.example.flimflare.util.ConstantsURL.API_KEY
import com.example.flimflare.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TVShowViewModel
@Inject constructor(
    private val repository: TvShowRepository
) : ViewModel() {

    private val _trendingOnThisWeekTvShow: MutableLiveData<Resource<TrendingTvShowResponse>> = MutableLiveData()
    val trendingOnThisWeekTvShow: LiveData<Resource<TrendingTvShowResponse>> get() = _trendingOnThisWeekTvShow

    private val _onTheAirTvShow: MutableLiveData<Resource<OnTheAirResponse>> = MutableLiveData()
    val onTheAirTvShow: LiveData<Resource<OnTheAirResponse>> get() = _onTheAirTvShow
    var onTheAirPage = 1

    private val _topRateTvShow: MutableLiveData<Resource<TopRateTvShowResponse>> = MutableLiveData()
    val topRateTvShow: LiveData<Resource<TopRateTvShowResponse>> get() = _topRateTvShow
    var topRatePage = 1

    private val _tvShowDetails: MutableLiveData<Resource<TvShowDetailsResponse>> = MutableLiveData()
    val tvShowDetails: LiveData<Resource<TvShowDetailsResponse>> get() = _tvShowDetails

    fun getTrendingOnTheWeekTvShow(apiKey: String, language: String) = viewModelScope.launch {
        _trendingOnThisWeekTvShow.postValue(Resource.Loading())
        val response = repository.getTrendingOnThisWeekTvShow(apiKey, language)
        _trendingOnThisWeekTvShow.postValue(handleTrendingTvShow(response))
    }

    fun getOnTheAirTvShow(apiKey: String, language: String) = viewModelScope.launch {
        _onTheAirTvShow.postValue(Resource.Loading())
        val response = repository.getOnTheAirTvShow(apiKey, language, onTheAirPage)
        _onTheAirTvShow.postValue(handleOnTheAirResponse(response))
    }

    fun getTopRateTvShow(apiKey: String, language: String) = viewModelScope.launch {
        _topRateTvShow.postValue(Resource.Loading())
        val response = repository.getTopRateTvShow(apiKey, language, topRatePage)
        _topRateTvShow.postValue(handleTopRateTvShow(response))
    }

    fun getTvShowDetails(showId: Int) = viewModelScope.launch {
        _tvShowDetails.postValue(Resource.Loading())
        val response = repository.getTvShowDetails(showId, API_KEY, "en")
        _tvShowDetails.postValue(handleTvShowDetails(response))
    }

    private fun handleTrendingTvShow(response: Response<TrendingTvShowResponse>): Resource<TrendingTvShowResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.errorBody()?.string() ?: "Unknown Error")
    }

    private fun handleOnTheAirResponse(response: Response<OnTheAirResponse>): Resource<OnTheAirResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.errorBody()?.string() ?: "Unknown Error")
    }

    private fun handleTopRateTvShow(response: Response<TopRateTvShowResponse>): Resource<TopRateTvShowResponse>? {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handleTvShowDetails(response: Response<TvShowDetailsResponse>): Resource<TvShowDetailsResponse>? {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }
}
