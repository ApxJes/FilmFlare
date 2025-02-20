package com.example.flimflare.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flimflare.model.tvShow.TrendingTvShowResponse
import com.example.flimflare.repository.TvShowRepository
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

    fun getTrendingOnTheWeekTvShow(apiKey: String, language: String) = viewModelScope.launch {
        _trendingOnThisWeekTvShow.postValue(Resource.Loading())
        val response = repository.getTrendingOnThisWeekTvShow(apiKey, language)
        _trendingOnThisWeekTvShow.postValue(handleTrendingTvShow(response))
    }


    private fun handleTrendingTvShow(response: Response<TrendingTvShowResponse>): Resource<TrendingTvShowResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.errorBody()?.string() ?: "Unknown Error")
    }
}
