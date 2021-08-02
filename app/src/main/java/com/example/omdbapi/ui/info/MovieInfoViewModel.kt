package com.example.omdbapi.ui.info

import androidx.lifecycle.*
import com.example.omdbapi.repository.MovieInfoRepository
import com.example.omdbapi.retrofit.MovieInfo
import com.example.omdbapi.retrofit.Search
import com.example.omdbapi.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel
@Inject constructor(
    private val movieInfoRepository: MovieInfoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<MovieInfo>> = MutableLiveData()
    private val _dataStateMore: MutableLiveData<DataState<List<Search>>> = MutableLiveData()

    val dataState: LiveData<DataState<MovieInfo>>
        get() = _dataState

    val dataStateMore: LiveData<DataState<List<Search>>>
        get() = _dataStateMore


    fun setStateEvent(movieInfoStateEvent: MovieInfoStateEvent, apiKey: String, id: String) {
        viewModelScope.launch {
            when (movieInfoStateEvent) {
                is MovieInfoStateEvent.GetMovieInfoEvents -> {
                    movieInfoRepository.getMovieInfo(apiKey, id)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }
                is MovieInfoStateEvent.None -> {
                    // who cares
                }
            }
        }
    }


    fun setMoreMovieStateEvent(movieInfoStateEvent: MovieInfoStateEvent, apiKey: String, name: String) {
        viewModelScope.launch {
            when (movieInfoStateEvent) {
                is MovieInfoStateEvent.GetMovieInfoEvents -> {
                    movieInfoRepository.getMovies(apiKey, name)
                        .onEach { dataState ->
                            _dataStateMore.value = dataState
                        }.launchIn(viewModelScope)
                }
                is MovieInfoStateEvent.None -> {
                    // who cares
                }
            }
        }
    }

}

sealed class MovieInfoStateEvent {
    object GetMovieInfoEvents : MovieInfoStateEvent()

    object None : MovieInfoStateEvent()
}