package com.example.flimflare.di

import com.example.flimflare.adapter.NowPlayingAdapter
import com.example.flimflare.adapter.PopularAdapter
import com.example.flimflare.api.API
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

}