package com.example.flimflare.repository.movieRepository

import com.example.flimflare.api.API
import com.example.flimflare.model.details.credits.CreditsResponse
import com.example.flimflare.model.details.movie.MovieDetailsResponse
import com.example.flimflare.model.details.person.PersonDetailsResponse
import com.example.flimflare.model.nowPlaying.NowPlayingResponse
import com.example.flimflare.model.popular.PopularResponse
import com.example.flimflare.model.topRate.TopRateResponse
import com.example.flimflare.model.upcoming.UpcomingResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRepository
@Inject constructor(
    private val api: API
){

    suspend fun getNowPlaying(apiKey: String, pageNumber: Int): Response<NowPlayingResponse> {
        return api.getNowPlaying(apiKey, pageNumber)
    }

    suspend fun getPopular(apiKey: String, pageNumber: Int): Response<PopularResponse> {
        return api.getPopular(apiKey, pageNumber)
    }

    suspend fun getTopRate(apiKey: String, pageNumber: Int): Response<TopRateResponse> {
        return api.getTopRate(apiKey, pageNumber)
    }

    suspend fun getUpcoming(apiKey: String, pageNumber: Int): Response<UpcomingResponse> {
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
}