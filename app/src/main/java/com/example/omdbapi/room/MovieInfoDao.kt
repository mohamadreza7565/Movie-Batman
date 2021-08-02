package com.example.omdbapi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.omdbapi.retrofit.Movie
import com.example.omdbapi.retrofit.MovieInfo
import com.example.omdbapi.retrofit.Search

@Dao
interface MovieInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieInfo): Long

    @Query("SELECT * FROM movie_info WHERE id LIKE :id")
    suspend fun get(id: String): MovieInfo
}