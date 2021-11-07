package com.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.RoomDatabase.ResultDao
import com.dataApi.MoviesServices
import com.pojo.GenersResponse
import com.pojo.NowPlayingResponse
import com.pojo.ResultX
import com.pojo.TopRatedResponse
import retrofit2.Response
import javax.inject.Inject


class MovieRepository @Inject constructor(private val moviesServices: MoviesServices, private val resultDao: ResultDao){

    suspend fun getTopRated(): Response<TopRatedResponse> {
        return moviesServices.getTopRatedMovies()
    }

    suspend fun getNowPlayingNow(): Response<NowPlayingResponse> {
        return moviesServices.getPlayingNowMovies()
    }

    suspend fun getGener(): Response<GenersResponse> {
        return moviesServices.getGeners()
    }

    suspend fun getAllMovies(): List<ResultX> {
        return resultDao.getAllMovies()
    }

    suspend fun insertMovie(movie: ResultX) {
        resultDao.insertMovie(movie)
    }

    suspend fun searchByName(name: String):List<ResultX>{
        return resultDao.getMovieByName(name)
    }
}