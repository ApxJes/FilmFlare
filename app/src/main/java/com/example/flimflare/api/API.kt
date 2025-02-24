package com.example.flimflare.api

import com.example.flimflare.model.credits.CreditsResponse
import com.example.flimflare.model.details.movie.MovieDetailsResponse
import com.example.flimflare.model.details.person.PersonDetailsResponse
import com.example.flimflare.model.movie.MovieResponse
import com.example.flimflare.model.details.show.TvShowDetailsResponse
import com.example.flimflare.model.tvShow.each_season_details.EachSeasonDetailsResponse
import com.example.flimflare.model.tvShow.TvShowResponse
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
    ): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int,
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRate(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): Response<MovieResponse>

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
    ): Response<MovieResponse>


    @GET("trending/tv/{time_window}")
    suspend fun getTrendingThisWeekTvShow(
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<TvShowResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShowResponse>

    @GET("tv/top_rated")
    suspend fun getTopRateTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShowResponse>

    @GET("tv/{series_id}")
    suspend fun getTvShowDetails(
        @Path("series_id") showId: Int,
        @Query("api_key") apiKey: String
    ): Response<TvShowDetailsResponse>

    @GET("tv/{series_id}/season/{season_number}")
    suspend fun getEachSeasonDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String
    ): Response<EachSeasonDetailsResponse>

    @GET("tv/{series_id}/season/{season_number}/credits")
    suspend fun getShowCredits(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String
    ): Response<CreditsResponse>

    @GET("search/tv")
    suspend fun getSearchTvShow(
        @Query("query") searchString: String,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>
}