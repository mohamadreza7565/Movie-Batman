package com.rymo.samplehilt.di

import android.content.Context
import androidx.room.Room
import com.example.omdbapi.room.MovieDao
import com.example.omdbapi.room.MovieInfoDao
import com.rymo.samplehilt.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {


    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(blogDatabase: AppDatabase) : MovieDao {
        return blogDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideMovieInfoDao(blogDatabase: AppDatabase) : MovieInfoDao {
        return blogDatabase.movieInfoDao()
    }


}