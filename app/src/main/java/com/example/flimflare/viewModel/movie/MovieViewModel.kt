package com.example.flimflare.viewModel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flimflare.model.credits.CreditsResponse
import com.example.flimflare.model.details.movie.MovieDetailsResponse
import com.example.flimflare.model.details.person.PersonDetailsResponse
import com.example.flimflare.model.movie.MovieResponse
import com.example.flimflare.model.room.MovieEntity
import com.example.flimflare.repository.movie.MovieRepository
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

    private val _nowPlayingResponse: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val nowPlayingResponse: LiveData<Resource<MovieResponse>> get() = _nowPlayingResponse
    var nowPlayingPage = 1

    private val _popularResponse: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val popularResponse: LiveData<Resource<MovieResponse>> get() = _popularResponse
    var popularPage = 1

    private val _topRateResponse: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val topRateResponse: LiveData<Resource<MovieResponse>> get() = _topRateResponse
    var topRatePage = 1

    private val _upcomingResponse: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val upcomingResponse: LiveData<Resource<MovieResponse>> get() = _upcomingResponse

    private val _movieDetailsResponse: MutableLiveData<Resource<MovieDetailsResponse>> = MutableLiveData()
    val movieDetailsResponse: LiveData<Resource<MovieDetailsResponse>> get() = _movieDetailsResponse

    private val _creditsResponse: MutableLiveData<Resource<CreditsResponse>> = MutableLiveData()
    val creditsResponse: LiveData<Resource<CreditsResponse>> get() = _creditsResponse

    private val _personDetailResponse: MutableLiveData<Resource<PersonDetailsResponse>> = MutableLiveData()
    val personDetailResponse: LiveData<Resource<PersonDetailsResponse>> get() = _personDetailResponse

    private val _searchResponse: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val searchResponse: LiveData<Resource<MovieResponse>> get() = _searchResponse
    var searchPage = 1

    lateinit var saveMovieList: LiveData<List<MovieEntity>>

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

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        _movieDetailsResponse.postValue(Resource.Loading())
        val response = repository.getDetails(movieId = movieId, apiKey = API_KEY)
        _movieDetailsResponse.postValue(handleMovieDetails(response))
    }

    fun getCredits(movieId: Int) = viewModelScope.launch {
        _creditsResponse.postValue(Resource.Loading())
        val response = repository.getCredits(movieId = movieId, apiKey = API_KEY)
        _creditsResponse.postValue(handleCredits(response))
    }

    fun getPersonDetails(personId: Int) = viewModelScope.launch {
        _personDetailResponse.postValue(Resource.Loading())
        val response = repository.getPersonDetails(personId = personId, apiKey = API_KEY)
        _personDetailResponse.postValue(handlePersonDetails(response))
    }

    fun getSearchMovie(query: String) = viewModelScope.launch {
        _searchResponse.postValue(Resource.Loading())
        val response = repository.getSearch(query = query, pageInt = searchPage, apiKey = API_KEY)
        _searchResponse.postValue(handleSearchResponse(response))
    }

    fun insertMovie(result: MovieEntity) = viewModelScope.launch {
        repository.insertMovie(result)
    }

    fun deleteMovie(result: MovieEntity) = viewModelScope.launch {
        repository.deleteMovie(result)
    }

    fun getAllMovie() = viewModelScope.launch {
        saveMovieList = repository.getAllMovie()
    }

    private fun handleNowPlaying(response: Response<MovieResponse>): Resource<MovieResponse> {
        if(response.isSuccessful) {
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handlePopular(response: Response<MovieResponse>): Resource<MovieResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handleTopRate(response: Response<MovieResponse>): Resource<MovieResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handleUpcoming(response: Response<MovieResponse>): Resource<MovieResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handleMovieDetails(response: Response<MovieDetailsResponse>): Resource<MovieDetailsResponse> {
        if(response.isSuccessful) {
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handleCredits(response: Response<CreditsResponse>): Resource<CreditsResponse> {
        if(response.isSuccessful) {
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handlePersonDetails(response: Response<PersonDetailsResponse>): Resource<PersonDetailsResponse>? {
        if(response.isSuccessful) {
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

    private fun handleSearchResponse(response: Response<MovieResponse>): Resource<MovieResponse>? {
        if(response.isSuccessful) {
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.errorBody().toString())
    }

}