package com.example.omdbapi.ui.info

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbapi.R
import com.example.omdbapi.retrofit.MovieInfo
import com.example.omdbapi.retrofit.Search
import com.example.omdbapi.util.DataState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_info.*

@AndroidEntryPoint
class MovieInfoActivity : AppCompatActivity() {

    val viewModel: MovieInfoViewModel by viewModels()
    lateinit var adapter: MoreMovieRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)

        imv_back.setOnClickListener { finish() }

        rv_movies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = MoreMovieRvAdapter(this)
        rv_movies.adapter = adapter

        subscribeObserver()
        viewModel.setStateEvent(
            MovieInfoStateEvent.GetMovieInfoEvents,
            "3e974fca",
            intent.getStringExtra("ID")!!
        )

        viewModel.setMoreMovieStateEvent(
            MovieInfoStateEvent.GetMovieInfoEvents,
            "3e974fca",
            "batman"
        )

    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<MovieInfo> -> {
                    displayProgressBar(false)
                    initData(dataState.data)
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

        viewModel.dataStateMore.observe(this, Observer { dataState ->
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
                }
            }

        })
    }

    private fun initRv(movies: List<Search>) {
        adapter.replace(movies)
    }

    @SuppressLint("SetTextI18n")
    private fun initData(movieInfo: MovieInfo) {
        tv_title.text = movieInfo.title
        rating_bar.rating = movieInfo.imdbRating.toFloat() / 2
        rating_bar.isEnabled = false
        tv_rating.text = movieInfo.imdbRating
        tv_year.text = movieInfo.year
        tv_rated.text = movieInfo.rated
        tv_released.text = movieInfo.released
        tv_runtime.text = movieInfo.runtime
        tv_genre.text = movieInfo.genre
        tv_language.text = movieInfo.language
        tv_country.text = movieInfo.country
        tv_boxOffice.text = movieInfo.boxOffice
        tv_vote_count.text = movieInfo.imdbVotes + " Rating"
        Picasso.get().load(movieInfo.poster).into(imv_poster)


    }


    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        rl_loading.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

}