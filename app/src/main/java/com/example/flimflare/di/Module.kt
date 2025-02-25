package com.example.flimflare.di

import com.example.flimflare.adapter.credit.CastAdapter
import com.example.flimflare.adapter.credit.CrewAdapter
import com.example.flimflare.adapter.movie.NowPlayingAdapter
import com.example.flimflare.adapter.tvShow.OnTheAirAdapter
import com.example.flimflare.adapter.movie.PopularAdapter
import com.example.flimflare.adapter.movie.SearchAdapter
import com.example.flimflare.adapter.tvShow.SeasonAdapter
import com.example.flimflare.adapter.tvShow.SeasonDetailsAdapter
import com.example.flimflare.adapter.tvShow.ShowCreatorAdapter
import com.example.flimflare.adapter.movie.TopRateAdapter
import com.example.flimflare.adapter.tvShow.TopRatingTvShowAdapter
import com.example.flimflare.adapter.tvShow.TrendingTvShowAdapter
import com.example.flimflare.adapter.movie.UpcomingAdapter
import com.example.flimflare.adapter.save.SaveAdapter
import com.example.flimflare.api.API
import com.example.flimflare.model.room.MovieEntity
import com.example.flimflare.util.ConstantsURL.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun providesRetrofitInstance(): Retrofit {
        val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesApi(retrofit: Retrofit): API{
        return retrofit.create(API::class.java)
    }

    @Singleton
    @Provides
    fun providesNowPlayingAdapter(): NowPlayingAdapter = NowPlayingAdapter()

    @Singleton
    @Provides
    fun providesPopularAdapter(): PopularAdapter = PopularAdapter()

    @Singleton
    @Provides
    fun providesTopRateAdapter(): TopRateAdapter = TopRateAdapter()

    @Singleton
    @Provides
    fun providesUpcomingAdapter(): UpcomingAdapter = UpcomingAdapter()

    @Singleton
    @Provides
    fun providesCastAdapter(): CastAdapter = CastAdapter()

    @Singleton
    @Provides
    fun providesCrewAdapter(): CrewAdapter = CrewAdapter()

    @Singleton
    @Provides
    fun providesSearchAdapter(): SearchAdapter = SearchAdapter()

    @Singleton
    @Provides
    fun providesTrendingAdapter(): TrendingTvShowAdapter = TrendingTvShowAdapter()

    @Singleton
    @Provides
    fun providesTrendingOnAirAdapter(): OnTheAirAdapter = OnTheAirAdapter()

    @Singleton
    @Provides
    fun providesTopRatingTvShowAdapter(): TopRatingTvShowAdapter = TopRatingTvShowAdapter()

    @Singleton
    @Provides
    fun providesShowCreatorAdapter(): ShowCreatorAdapter = ShowCreatorAdapter()

    @Singleton
    @Provides
    fun providesSeasonAdapter(): SeasonAdapter = SeasonAdapter()

    @Singleton
    @Provides
    fun providesSeasonDetailsAdapter(): SeasonDetailsAdapter = SeasonDetailsAdapter()

}
