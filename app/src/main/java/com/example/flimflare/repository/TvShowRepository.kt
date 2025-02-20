package com.example.flimflare.repository

import android.util.Log
import com.example.flimflare.api.API
import com.example.flimflare.model.tvShow.TrendingTvShowResponse
import retrofit2.Response
import javax.inject.Inject

class TvShowRepository
@Inject constructor(
    private val api: API
) {
    suspend fun getTrendingOnThisWeekTvShow(apiKey: String, language: String): Response<TrendingTvShowResponse> {
        return api.getTrendingThisWeekTvShow("week", apiKey, language)
    }
}
