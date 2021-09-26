package com.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dataApi.*
import androidx.lifecycle.MutableLiveData
import com.RoomDatabase.AppDatabase
import com.pojo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(application: Application): AndroidViewModel(application) {

    var movieMutableLiveData = MutableLiveData<MutableList<ResultX>>()
    var GenersMutableLiveData = MutableLiveData<MutableList<Genre>>()
    var FavoritesMutableLiveData = MutableLiveData<MutableList<ResultX>>()
    var movieDatabase: AppDatabase = AppDatabase.getInstance(this.getApplication())

    fun getNowPlayingNow(){

        val movieCall: Call<NowPlayingResponse> = JSONRetriever().getNowPlayingNow()
        movieCall.enqueue(object : Callback<NowPlayingResponse> {
            override fun onResponse(call: Call<NowPlayingResponse>, response: Response<NowPlayingResponse>) {
                movieMutableLiveData.value= response.body()?.results as MutableList<ResultX>
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {

            }
        })
    }

    fun getTopRated(){

        val movieCall: Call<TopRatedResponse> = JSONRetriever().getTopRated()
        movieCall.enqueue(object : Callback<TopRatedResponse> {
            override fun onResponse(call: Call<TopRatedResponse>, response: Response<TopRatedResponse>) {
                movieMutableLiveData.value = response.body()?.results as MutableList<ResultX>
            }

            override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {

            }
        })

    }


    fun getGeners(){
        val movieCall: Call<Geners> = JSONRetriever().getGener()
        movieCall.enqueue(object : Callback<Geners> {
            override fun onResponse(call: Call<Geners>, response: Response<Geners>) {
                GenersMutableLiveData.value= response.body()?.genres as MutableList<Genre>
            }

            override fun onFailure(call: Call<Geners>, t: Throwable) {

            }
        })
    }

    fun getFavorites(){
        FavoritesMutableLiveData.value=movieDatabase.getMovieDao().getAllMovies() as MutableList<ResultX>

    }

    fun inserFavorites(movie: ResultX){
        movieDatabase.getMovieDao().insertMovie(movie)
    }


}