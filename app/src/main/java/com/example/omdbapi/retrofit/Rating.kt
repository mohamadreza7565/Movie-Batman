package com.example.omdbapi.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rating(

    @SerializedName("Source")
    @Expose
    val source:String,


    @SerializedName("Value")
    @Expose
    val value:String



){

}
