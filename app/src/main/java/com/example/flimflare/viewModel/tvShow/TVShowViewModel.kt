package com.example.flimflare.viewModel.tvShow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flimflare.model.credits.CreditsResponse
import com.example.flimflare.model.tvShow.each_season_details.EachSeasonDetailsResponse
import com.example.flimflare.model.details.show.TvShowDetailsResponse
import com.example.flimflare.model.room.TvShowEntity
import com.example.flimflare.model.tvShow.TvShowResponse
import com.example.flimflare.repository.tvShow.TvShowRepository
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

    private val _trendingOnThisWeekTvShow: MutableLiveData<Resource<TvShowResponse>> = MutableLiveData()
    val trendingOnThisWeekTvShow: LiveData<Resource<TvShowResponse>> get() = _trendingOnThisWeekTvShow

    private val _onTheAirTvShow: MutableLiveData<Resource<TvShowResponse>> = MutableLiveData()
    val onTheAirTvShow: LiveData<Resource<TvShowResponse>> get() = _onTheAirTvShow
    var onTheAirPage = 1

    private val _topRateTvShow: MutableLiveData<Resource<TvShowResponse>> = MutableLiveData()
    val topRateTvShow: LiveData<Resource<TvShowResponse>> get() = _topRateTvShow
    var topRatePage = 1

    lateinit var saveTvShowList: LiveData<List<TvShowEntity>>

    private val _tvShowDetails: MutableLiveData<Resource<TvShowDetailsResponse>> = MutableLiveData()
    val tvShowDetails: LiveData<Resource<TvShowDetailsResponse>> get() = _tvShowDetails

    private val _eachSeasonDetails: MutableLiveData<Resource<EachSeasonDetailsResponse>> = MutableLiveData()
    val eachSeasonDetails: LiveData<Resource<EachSeasonDetailsResponse>> get() = _eachSeasonDetails

    private val _showCredits: MutableLiveData<Resource<CreditsResponse>> = MutableLiveData()
    val showCredits: LiveData<Resource<CreditsResponse>> = _showCredits

    private val _searchTvShow: MutableLiveData<Resource<TvShowResponse>> = MutableLiveData()
    val searchTvShow: LiveData<Resource<TvShowResponse>> get() = _searchTvShow

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
        val response = repository.getTvShowDetails(showId, API_KEY)
        _tvShowDetails.postValue(handleTvShowDetails(response))
    }

    fun getEachSeasonDetails(seriesId: Int, seasonNumber: Int) = viewModelScope.launch {
        _eachSeasonDetails.postValue(Resource.Loading())
        val response = repository.getEachSeasonDetails(seriesId, seasonNumber, API_KEY)
        _eachSeasonDetails.postValue(handleEachSeasonDetails(response))
    }

    fun getShowCredits(seriesId: Int, seasonNumber: Int) = viewModelScope.launch {
        _showCredits.postValue(Resource.Loading())
        val response = repository.getShowCredits(seriesId, seasonNumber, API_KEY)
        _showCredits.postValue(handleShowCredits(response))

    }

    fun getSearchTvShow(searchString: String) = viewModelScope.launch {
        _searchTvShow.postValue(Resource.Loading())
        val response = repository.getSearchTvShow(searchString, API_KEY)
        _searchTvShow.postValue(handleSearchTvShow(response))
    }

    fun saveTvShow(show: TvShowEntity) = viewModelScope.launch {
        repository.insertTvShow(show)
    }

    fun deleteTvShow(show: TvShowEntity) = viewModelScope.launch {
        repository.deleteTvShow(show)
    }

    fun getAllTvShow() = viewModelScope.launch {
        saveTvShowList = repository.getAllTvShow()
    }

    private fun handleTrendingTvShow(response: Response<TvShowResponse>): Resource<TvShowResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.errorBody()?.string() ?: "Unknown Error")
    }

    private fun handleOnTheAirResponse(response: Response<TvShowResponse>): Resource<TvShowResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.errorBody()?.string() ?: "Unknown Error")
    }

    private fun handleTopRateTvShow(response: Response<TvShowResponse>): Resource<TvShowResponse> {
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

    private fun handleEachSeasonDetails(response: Response<EachSeasonDetailsResponse>): Resource<EachSeasonDetailsResponse>? {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handleShowCredits(response: Response<CreditsResponse>): Resource<CreditsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.errorBody()?.string() ?: "Unknown Error")
    }

    private fun handleSearchTvShow(response: Response<TvShowResponse>): Resource<TvShowResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.errorBody()?.string() ?: "Unknown Error")
    }
}
