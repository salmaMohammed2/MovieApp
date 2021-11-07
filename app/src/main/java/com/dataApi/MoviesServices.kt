package com.dataApi

import com.pojo.GenersResponse
import com.pojo.NowPlayingResponse
import com.pojo.TopRatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY ="f1d39bfcda1c4bee39caf76d9c33190c"
interface MoviesServices {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key")apiKey :String = API_KEY): Response<TopRatedResponse>

    @GET("movie/now_playing")
    suspend fun getPlayingNowMovies(@Query("api_key")apiKey :String = API_KEY): Response<NowPlayingResponse>

    @GET("genre/movie/list")
    suspend fun getGeners(@Query("api_key")apiKey :String = API_KEY): Response<GenersResponse>
}