package com.example.omdbapi.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Search")
    @Expose
    var search: List<Search>
) {

}