package com.di

import android.app.Application
import com.dataApi.MoviesServices
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

const val BASE_URL = "https://api.themoviedb.org/3/"
@Module
@InstallIn(SingletonComponent::class)
class JSONRetriever {

    @Provides
    @Singleton
    fun provideMoviesServices (): MoviesServices {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MoviesServices::class.java)
    }
/*    private val service: MoviesServices

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(MoviesServices::class.java)
        // val movieRepository = MovieRepository(services)
    }

    suspend fun getTopRated() = service.getTopRatedMovies()
    suspend fun getNowPlayingNow() = service.getPlayingNowMovies()
    suspend fun getGener()=service.getGeners()
    */
}


