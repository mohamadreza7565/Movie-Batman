package com.example.omdbapi.repository

import com.example.omdbapi.room.MovieDao
import com.example.omdbapi.util.DataState
import com.rymo.samplehilt.retrofit.MoviesRetrofit
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val moviesRetrofit: MoviesRetrofit,
    private val movieDao: MovieDao
) {
    suspend fun getMovies(apiKey: String, name: String) = flow {
        if (movieDao.get().isEmpty())
            emit(DataState.Loading)
        else {
            emit(DataState.Success(movieDao.get()))
        }

        try {
            val networkMovies = moviesRetrofit.getMovies(apiKey, name)
            val networkSearch = networkMovies.search
            for (movie in networkSearch) {
                movieDao.insert(movie)
            }
            emit(DataState.Success(networkSearch))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}