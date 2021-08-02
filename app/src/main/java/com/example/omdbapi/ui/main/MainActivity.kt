package com.example.omdbapi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.omdbapi.R
import com.example.omdbapi.retrofit.Movie
import com.example.omdbapi.retrofit.Search
import com.example.omdbapi.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    val imageList = ArrayList<SlideModel>() // Create image list
    lateinit var adapter: MoviesRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSlider()

        rv_movies.layoutManager = LinearLayoutManager(this)
        adapter = MoviesRvAdapter(this)
        rv_movies.adapter = adapter

        subscribeObserver()
        viewModel.setStateEvent(MainStateEvent.GetMovieEvents,"3e974fca","batman")
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Search>> -> {
                    displayProgressBar(false)
                    initRv(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }

        })
    }


    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun initRv(movies: List<Search>) {
        adapter.replace(movies)
    }

    private fun initSlider() {
        imageList.add(SlideModel("https://image.slidesharecdn.com/presentation-avatarfilmposteranalysis-150303060114-conversion-gate01/95/avatar-film-poster-analysis-1-638.jpg"))
        imageList.add(SlideModel("https://www.komar.de/en/media/catalog/product/cache/5/image/9df78eab33525d08d6e5fb8d27136e95/8/-/8-4114_star_wars_movie_poster_wide_ma.jpg"))
        imageList.add(SlideModel("https://d2kektcjb0ajja.cloudfront.net/images/posts/feature_images/000/000/072/large-1466557422-feature.jpg"))
        slider.setImageList(imageList, ScaleTypes.FIT)
    }
}