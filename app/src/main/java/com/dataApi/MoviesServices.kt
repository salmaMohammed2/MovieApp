package com.dataApi

import com.pojo.Geners
import com.pojo.NowPlayingResponse
import com.pojo.TopRatedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY ="f1d39bfcda1c4bee39caf76d9c33190c"
interface MoviesServices {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key")apiKey :String = API_KEY): Call<TopRatedResponse>

    @GET("movie/now_playing")
    fun getPlayingNowMovies(@Query("api_key")apiKey :String = API_KEY): Call<NowPlayingResponse>

    @GET("genre/movie/list")
    fun getGeners(@Query("api_key")apiKey :String = API_KEY): Call<Geners>
}