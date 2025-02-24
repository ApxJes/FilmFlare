package com.example.flimflare.model.tvShow


data class TvShowResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)