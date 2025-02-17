package com.example.flimflare.repository.movieRepository

import com.example.flimflare.api.API
import com.example.flimflare.model.nowPlaying.NowPlayingResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRepository
@Inject constructor(
    private val api: API
){

    suspend fun getNowPlaying(apiKey: String, pageNumber: Int): Response<NowPlayingResponse> {
        return api.getNowPlaying(apiKey, pageNumber)
    }
}