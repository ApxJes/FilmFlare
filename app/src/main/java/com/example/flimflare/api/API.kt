package com.example.flimflare.api

import com.example.flimflare.model.details.movie.MovieDetailsResponse
import com.example.flimflare.model.nowPlaying.NowPlayingResponse
import com.example.flimflare.model.popular.PopularResponse
import com.example.flimflare.model.topRate.TopRateResponse
import com.example.flimflare.model.upcoming.UpcomingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): Response<NowPlayingResponse>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int,
    ): Response<PopularResponse>

    @GET("movie/top_rated")
    suspend fun getTopRate(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): Response<TopRateResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): Response<UpcomingResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path ("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetailsResponse>
}