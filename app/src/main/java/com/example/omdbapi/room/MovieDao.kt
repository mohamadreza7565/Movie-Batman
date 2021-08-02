package com.example.omdbapi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.omdbapi.retrofit.Movie
import com.example.omdbapi.retrofit.Search

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(search: Search) : Long

    @Query("SELECT * FROM movies")
    suspend fun get() : List<Search>


}