package com.rymo.samplehilt.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.omdbapi.retrofit.Movie
import com.example.omdbapi.retrofit.MovieInfo
import com.example.omdbapi.retrofit.Search
import com.example.omdbapi.room.MovieDao
import com.example.omdbapi.room.MovieInfoDao

@Database(entities = [Search::class, MovieInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun movieInfoDao(): MovieInfoDao

    companion object {
        val DATABASE_NAME = "db_movies"
    }

}