package com.example.flimflare.model.tvShow.onTheAir

data class OnTheAirResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)