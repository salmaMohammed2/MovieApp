package com.dataApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/"
class JSONRetriever {
    private val service: MoviesServices

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(MoviesServices::class.java)
    }

    fun getTopRated() = service.getTopRatedMovies()
    fun getNowPlayingNow() = service.getPlayingNowMovies()
    fun getGener()=service.getGeners()
}