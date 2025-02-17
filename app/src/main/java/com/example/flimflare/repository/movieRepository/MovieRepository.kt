package com.example.flimflare.repository.movieRepository

import com.example.flimflare.api.API
import com.example.flimflare.model.nowPlaying.NowPlayingResponse
import com.example.flimflare.model.popular.PopularResponse
import com.example.flimflare.model.topRate.TopRateResponse
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
}