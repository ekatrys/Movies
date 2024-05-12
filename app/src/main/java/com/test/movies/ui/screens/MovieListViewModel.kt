package com.test.movies.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.test.movies.MoviesApplication
import com.test.movies.data.MoviesRepository
import com.test.movies.model.Movie
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MoviesUiState {
    data class Success(val moves: List<Movie>) : MoviesUiState
    data object Error : MoviesUiState
    data object Loading : MoviesUiState
}

class MovieListViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    var moviesUiState: MoviesUiState by mutableStateOf(MoviesUiState.Loading)
        private set

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            moviesUiState = MoviesUiState.Loading
            try {
                val listResult = moviesRepository.getMoviesOfTheDay().results
                moviesUiState = MoviesUiState.Success(listResult)

            } catch (e: IOException) {
                moviesUiState = MoviesUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MoviesApplication)
                val moviesRepository = application.container.moviesRepository
                MovieListViewModel(moviesRepository = moviesRepository)
            }
        }
    }
}