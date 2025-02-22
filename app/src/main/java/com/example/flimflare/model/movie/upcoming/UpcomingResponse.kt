package com.example.flimflare.model.upcoming

data class UpcomingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<UpcomingResult>,
    val total_pages: Int,
    val total_results: Int
)