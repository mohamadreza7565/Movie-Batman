package com.rymo.samplehilt.retrofit

import com.example.omdbapi.retrofit.Movie
import com.example.omdbapi.retrofit.MovieInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesRetrofit {

    @GET(".")
    suspend fun getMovies(
        @Query("apikey") apikey: String,
        @Query("s") name: String
    ): Movie

    @GET(".")
    suspend fun getMovieInfo(
        @Query("apikey") apikey: String,
        @Query("i") id: String
    ): MovieInfo

}