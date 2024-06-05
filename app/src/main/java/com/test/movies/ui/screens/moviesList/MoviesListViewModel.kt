package com.test.movies.ui.screens.moviesList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.movies.data.MoviesRepository
import com.test.movies.model.Movie
import com.test.movies.ui.screens.movieDetails.UiState
import kotlinx.coroutines.launch
import java.io.IOException

class MovieListViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    var moviesUiState: UiState<List<Movie>> by mutableStateOf(UiState.Loading)
        private set

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            moviesUiState = UiState.Loading
            try {
                val listResult = moviesRepository.getMoviesOfTheDay(1).results
                moviesUiState = UiState.Success(listResult)

            } catch (e: IOException) {
                moviesUiState = UiState.Error
            }
        }
    }
}