package com.example.flimflare.repository.tvShow

import com.example.flimflare.api.API
import com.example.flimflare.model.room.SaveDao
import com.example.flimflare.model.room.TvShowEntity
import com.example.flimflare.model.tvShow.TvShowResponse
import retrofit2.Response
import javax.inject.Inject

class TvShowRepository
@Inject constructor(
    private val api: API,
    private val dao: SaveDao
) {
    suspend fun getTrendingOnThisWeekTvShow(apiKey: String, language: String): Response<TvShowResponse> {
        return api.getTrendingThisWeekTvShow("week", apiKey, language)
    }

    suspend fun getOnTheAirTvShow(apiKey: String, language: String, page: Int) =
        api.getOnTheAirTvShow(apiKey, language, page)

    suspend fun getTopRateTvShow(apiKey: String, language: String, page: Int) =
        api.getTopRateTvShow(apiKey, language, page)

    suspend fun getTvShowDetails(showId: Int, apiKey: String) =
        api.getTvShowDetails(showId, apiKey)

    suspend fun getEachSeasonDetails(seriesId: Int, seasonNumber: Int, apiKey: String) =
        api.getEachSeasonDetails(seriesId, seasonNumber, apiKey)

    suspend fun getShowCredits(seriesId: Int, seasonNumber: Int, apiKey: String) =
        api.getShowCredits(seriesId, seasonNumber, apiKey)

    suspend fun getSearchTvShow(searchString: String, apiKey: String) =
        api.getSearchTvShow(searchString, apiKey)

    suspend fun insertTvShow(show: TvShowEntity) = dao.insertTvShow(show)

    suspend fun deleteTvShow(show: TvShowEntity) = dao.deleteShow(show)

    fun getAllTvShow() = dao.getAllTvShow()
}
