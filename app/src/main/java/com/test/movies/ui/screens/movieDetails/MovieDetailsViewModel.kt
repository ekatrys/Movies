package com.test.movies.ui.screens.movieDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.movies.data.MoviesRepository
import com.test.movies.model.Actor
import com.test.movies.model.MovieDetails
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface UiState< out T> {
    data class Success<T>(val data: T) : UiState<T>
    data object Error : UiState<Nothing>
    data object Loading : UiState<Nothing>
}

class MovieDetailsViewModel(
    movieId: String,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var movieDetailsUiState: UiState<MovieDetails> by mutableStateOf(UiState.Loading)
        private set

    var movieCst: List<Actor> = mutableListOf(Actor(1, "" , ""))
        private set

    init {
        getMovieDetails(movieId)
    }

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            movieDetailsUiState = UiState.Loading
            try {
                val movieDetails = moviesRepository.getMovieDetails(movieId)
                movieCst = moviesRepository.getMovieCredits(movieId).cast
                movieDetailsUiState = UiState.Success(movieDetails)

            } catch (e: IOException) {
                movieDetailsUiState = UiState.Error
            }
        }
    }
}