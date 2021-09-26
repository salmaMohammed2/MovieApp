package com.RoomDatabase

import android.content.Context
import androidx.room.*
import com.pojo.ResultX
import com.pojo.myConverters

@Database(entities = [ResultX::class], version = 1)
@TypeConverters(myConverters::class)
abstract class AppDatabase : RoomDatabase() {
   abstract fun getMovieDao() : ResultDao

   companion object {
       @Volatile private var INSTANCE: AppDatabase? = null

       fun getInstance(context: Context): AppDatabase =
           INSTANCE ?: synchronized(this) {
               INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
           }

       private fun buildDatabase(context: Context) =
           Room.databaseBuilder(context.applicationContext,
               AppDatabase::class.java, "Movie_Database")
               .allowMainThreadQueries()
               .fallbackToDestructiveMigration()
               .build()
   }


}