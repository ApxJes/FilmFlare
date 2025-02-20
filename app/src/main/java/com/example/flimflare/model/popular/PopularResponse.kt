package com.example.flimflare.model.popular

data class PopularResponse(
    val page: Int,
    val results: List<PopularResult>,
    val total_pages: Int,
    val total_results: Int
)