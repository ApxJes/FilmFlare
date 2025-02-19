package com.example.flimflare.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flimflare.model.trending.TrendingTvShowResponse
import com.example.flimflare.repository.TvShowRepository
import com.example.flimflare.util.ConstantsURL.API_KEY
import com.example.flimflare.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class KdramaViweModel
@Inject constructor(
    private val repository: TvShowRepository
): ViewModel(){
    private val _trendingKdramaResponse: MutableLiveData<Resource<TrendingTvShowResponse>> = MutableLiveData()
    val trendingKdramaResponse: LiveData<Resource<TrendingTvShowResponse>> = _trendingKdramaResponse

    fun getTrendingKdrama(language: String) = viewModelScope.launch {
        _trendingKdramaResponse.postValue(Resource.Loading())
        val response = repository.getTrendingTvShow(apiKey = API_KEY, language = language)
        _trendingKdramaResponse.postValue(handleTrendingAmericanTvShow(response))
    }

    private fun handleTrendingAmericanTvShow(response: Response<TrendingTvShowResponse>): Resource<TrendingTvShowResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                val filteredList = resultResponse.results.filter { "KR" in it.origin_country }
                return Resource.Success(
                    TrendingTvShowResponse(
                    page = resultResponse.page,
                    results = filteredList,
                    total_pages = resultResponse.total_pages,
                    total_results = resultResponse.total_results
                )
                )
            }
        }
        return Resource.Error(response.errorBody()?.string() ?: "Unknown Error")
    }
}