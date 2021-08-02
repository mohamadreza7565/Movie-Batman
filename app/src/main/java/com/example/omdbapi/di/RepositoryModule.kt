package com.example.omdbapi.di

import com.example.omdbapi.repository.MainRepository
import com.example.omdbapi.repository.MovieInfoRepository
import com.example.omdbapi.room.MovieDao
import com.example.omdbapi.room.MovieInfoDao
import com.rymo.samplehilt.retrofit.MoviesRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        moviesRetrofit: MoviesRetrofit,
        movieDao: MovieDao
    ): MainRepository {
        return MainRepository(moviesRetrofit, movieDao)
    }

    @Singleton
    @Provides
    fun provideMovieInfoRepository(
        moviesRetrofit: MoviesRetrofit,
        movieInfoDao: MovieInfoDao,
        movieDao: MovieDao
    ): MovieInfoRepository {
        return MovieInfoRepository(moviesRetrofit, movieInfoDao,movieDao)
    }

}