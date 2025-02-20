package com.example.flimflare.api

import com.example.flimflare.model.details.credits.CreditsResponse
import com.example.flimflare.model.details.movie.MovieDetailsResponse
import com.example.flimflare.model.details.person.PersonDetailsResponse
import com.example.flimflare.model.nowPlaying.NowPlayingResponse
import com.example.flimflare.model.popular.PopularResponse
import com.example.flimflare.model.search.SearchMovieResponse
import com.example.flimflare.model.showDetails.TvShowDetailsResponse
import com.example.flimflare.model.topRate.TopRateResponse
import com.example.flimflare.model.tvShow.onTheAir.OnTheAirResponse
import com.example.flimflare.model.tvShow.topRate.TopRateTvShowResponse
import com.example.flimflare.model.tvShow.trending.TrendingTvShowResponse
import com.example.flimflare.model.upcoming.UpcomingResponse
import com.example.flimflare.util.ConstantsURL.API_KEY
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

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path ("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<CreditsResponse>

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
        @Path ("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Response<PersonDetailsResponse>

    @GET("search/movie")
    suspend fun searchingMovie(
        @Query("query") searchMovie: String,
        @Query("language") pageSize: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<SearchMovieResponse>


    @GET("trending/tv/{time_window}")
    suspend fun getTrendingThisWeekTvShow(
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<TrendingTvShowResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<OnTheAirResponse>

    @GET("tv/top_rated")
    suspend fun getTopRateTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TopRateTvShowResponse>

    @GET("tv/{series_id}")
    suspend fun getTvShowDetails(
        @Path("show_id") showId: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String
    ): Response<TvShowDetailsResponse>

}