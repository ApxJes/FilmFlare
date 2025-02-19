package com.example.flimflare.repository

import com.example.flimflare.api.API
import javax.inject.Inject

class TvShowRepository
@Inject constructor(
    private val api: API
){
    suspend fun getTrendingTvShow(apiKey: String, language: String) =
        api.getTrendingTvShowsByWeek(language = language, apiKey = apiKey)
}