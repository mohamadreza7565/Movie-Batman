package com.example.omdbapi.repository

import com.example.omdbapi.room.MovieDao
import com.example.omdbapi.room.MovieInfoDao
import com.example.omdbapi.util.DataState
import com.rymo.samplehilt.retrofit.MoviesRetrofit
import kotlinx.coroutines.flow.flow

class MovieInfoRepository
constructor(
    private val moviesRetrofit: MoviesRetrofit,
    private val movieInfoDao: MovieInfoDao,
    private val movieDao: MovieDao
) {
    suspend fun getMovieInfo(apiKey: String, id: String) = flow {
        if (movieInfoDao.get(id) == null)
            emit(DataState.Loading)
        else {
            emit(DataState.Success(movieInfoDao.get(id)))
        }

        try {
            val networkMovies = moviesRetrofit.getMovieInfo(apiKey, id)
            movieInfoDao.insert(networkMovies)

            emit(DataState.Success(networkMovies))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getMovies(apiKey: String, name: String) = flow {
        if (movieDao.get().isEmpty())
            emit(DataState.Loading)
        else {
            emit(DataState.Success(movieDao.get()))
        }

        try {
            val networkMovies = moviesRetrofit.getMovies(apiKey, name)
            val networkSearch = networkMovies.search

            emit(DataState.Success(networkSearch))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}