package com.example.flimflare.model.movie.topRate

data class TopRateResponse(
    val page: Int,
    val results: List<TopRateResult>,
    val total_pages: Int,
    val total_results: Int
)