package com.RoomDatabase

import androidx.room.*
import com.pojo.ResultX

@Dao
interface ResultDao {
    @Query("Select * From MOVIE_TABLE")
    suspend fun getAllMovies():List<ResultX>

    @Query("Select * From MOVIE_TABLE where title = :name")
    suspend fun getMovieByName(name: String): List<ResultX>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: ResultX)

}

