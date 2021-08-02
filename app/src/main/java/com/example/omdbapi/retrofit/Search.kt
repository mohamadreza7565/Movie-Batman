package com.example.omdbapi.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Search(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("imdbID")
    @Expose
    val id : String,

    @SerializedName("Title")
    @Expose
    val title : String,

    @SerializedName("Year")
    @Expose
    val year : String,

    @SerializedName("Type")
    @Expose
    val type : String ,

    @SerializedName("Poster")
    @Expose
    val poster : String
){

}
