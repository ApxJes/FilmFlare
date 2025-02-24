package com.example.flimflare.repository.movie

import android.graphics.Movie
import androidx.lifecycle.LiveData
import com.example.flimflare.api.API
import com.example.flimflare.model.credits.CreditsResponse
import com.example.flimflare.model.details.movie.MovieDetailsResponse
import com.example.flimflare.model.details.person.PersonDetailsResponse
import com.example.flimflare.model.movie.MovieResponse
import com.example.flimflare.model.room.MovieEntity
import com.example.flimflare.model.room.SaveDao
import retrofit2.Response
import javax.inject.Inject

class MovieRepository
@Inject constructor(
    private val api: API,
    private val dao: SaveDao
){

    suspend fun getNowPlaying(apiKey: String, pageNumber: Int): Response<MovieResponse> {
        return api.getNowPlaying(apiKey, pageNumber)
    }

    suspend fun getPopular(apiKey: String, pageNumber: Int): Response<MovieResponse> {
        return api.getPopular(apiKey, pageNumber)
    }

    suspend fun getTopRate(apiKey: String, pageNumber: Int): Response<MovieResponse> {
        return api.getTopRate(apiKey, pageNumber)
    }

    suspend fun getUpcoming(apiKey: String, pageNumber: Int): Response<MovieResponse> {
        return api.getUpcoming(apiKey, pageNumber)
    }

    suspend fun getDetails(movieId: Int, apiKey: String): Response<MovieDetailsResponse> {
        return api.getMovieDetails(movieId, apiKey)
    }

    suspend fun getCredits(movieId: Int, apiKey: String): Response<CreditsResponse> {
        return api.getCredits(movieId, apiKey)
    }

    suspend fun getPersonDetails(personId: Int, apiKey: String): Response<PersonDetailsResponse> {
        return api.getPersonDetails(personId, apiKey)
    }

    suspend fun getSearch(query: String, pageInt: Int, apiKey: String): Response<MovieResponse> {
        return api.searchingMovie(query, pageInt, apiKey)
    }

    suspend fun insertMovie(result: MovieEntity) = dao.insertMovie(result)

    suspend fun deleteMovie(result: MovieEntity) = dao.deleteMovie(result)

    fun getAllMovie() = dao.getAllMovie()

    fun getMovieById(movieId: Int) = dao.getMovieById(movieId)
}