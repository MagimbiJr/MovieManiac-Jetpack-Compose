package com.tana.moviemaniac.di

import com.tana.moviemaniac.data.remote.MoviesApi
import com.tana.moviemaniac.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Provides
    fun provideMovieApi(): MoviesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MoviesApi::class.java)
    }

}