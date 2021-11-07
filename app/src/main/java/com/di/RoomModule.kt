package com.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.RoomDatabase.AppDatabase
import com.RoomDatabase.ResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context):AppDatabase {
        return Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, "Movie_Database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    //@Singleton
    fun provideResultDao(appDatabase: AppDatabase):ResultDao{
        return appDatabase.getMovieDao()
    }


}