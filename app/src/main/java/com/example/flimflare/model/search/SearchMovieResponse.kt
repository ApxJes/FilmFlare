package com.example.flimflare.model.search

data class SearchMovieResponse(
    val page: Int,
    val results: MutableList<SearchMovieResult>,
    val total_pages: Int,
    val total_results: Int
)