package com.example.omdbapi.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.omdbapi.repository.MainRepository
import com.example.omdbapi.retrofit.Movie
import com.example.omdbapi.retrofit.Search
import com.example.omdbapi.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Search>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Search>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent, apiKey: String, name: String) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetMovieEvents -> {
                    mainRepository.getMovies(apiKey,name)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }
                is MainStateEvent.None -> {
                    // who cares
                }
            }
        }
    }

}

sealed class MainStateEvent {
    object GetMovieEvents : MainStateEvent()

    object None : MainStateEvent()
}