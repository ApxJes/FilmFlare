package com.example.flimflare.di

import com.example.flimflare.adapter.credit.CastAdapter
import com.example.flimflare.adapter.credit.CrewAdapter
import com.example.flimflare.adapter.movie.NowPlayingAdapter
import com.example.flimflare.adapter.movie.PopularAdapter
import com.example.flimflare.adapter.movie.SearchAdapter
import com.example.flimflare.adapter.movie.TopRateAdapter
import com.example.flimflare.adapter.movie.UpcomingAdapter
import com.example.flimflare.adapter.tvShow.OnTheAirAdapter
import com.example.flimflare.adapter.tvShow.SearchTvShowAdapter
import com.example.flimflare.adapter.tvShow.SeasonAdapter
import com.example.flimflare.adapter.tvShow.SeasonDetailsAdapter
import com.example.flimflare.adapter.tvShow.ShowCreatorAdapter
import com.example.flimflare.adapter.tvShow.TopRatingTvShowAdapter
import com.example.flimflare.adapter.tvShow.TrendingTvShowAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {

    @Provides
    fun providesNowPlayingAdapter(): NowPlayingAdapter = NowPlayingAdapter()

    @Provides
    fun providesPopularAdapter(): PopularAdapter = PopularAdapter()

    @Provides
    fun providesTopRateAdapter(): TopRateAdapter = TopRateAdapter()

    @Provides
    fun providesUpcomingAdapter(): UpcomingAdapter = UpcomingAdapter()

    @Provides
    fun providesCastAdapter(): CastAdapter = CastAdapter()

    @Provides
    fun providesCrewAdapter(): CrewAdapter = CrewAdapter()

    @Provides
    fun providesSearchAdapter(): SearchAdapter = SearchAdapter()

    @Provides
    fun providesTrendingAdapter(): TrendingTvShowAdapter = TrendingTvShowAdapter()

    @Provides
    fun providesTrendingOnAirAdapter(): OnTheAirAdapter = OnTheAirAdapter()

    @Provides
    fun providesTopRatingTvShowAdapter(): TopRatingTvShowAdapter = TopRatingTvShowAdapter()

    @Provides
    fun providesShowCreatorAdapter(): ShowCreatorAdapter = ShowCreatorAdapter()

    @Provides
    fun providesSeasonAdapter(): SeasonAdapter = SeasonAdapter()

    @Provides
    fun providesSeasonDetailsAdapter(): SeasonDetailsAdapter = SeasonDetailsAdapter()

    @Provides
    fun providesSearchTvShowAdapter(): SearchTvShowAdapter = SearchTvShowAdapter()
}