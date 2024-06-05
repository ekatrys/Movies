package com.test.movies.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.test.movies.ui.screens.movieDetails.MovieDetailsScreen
import com.test.movies.ui.screens.movieDetails.MovieDetailsViewModel
import com.test.movies.ui.screens.moviesList.MovieListViewModel
import com.test.movies.ui.screens.moviesList.MoviesScreen
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

enum class MoviesScreen {
    MoviesList
}

@Composable
fun MoviesNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MoviesScreen.MoviesList.name,
        modifier = modifier
    ) {
        composable(route = MoviesScreen.MoviesList.name) {
            val moviesViewModel: MovieListViewModel by inject()
            MoviesScreen(
                moviesUiState = moviesViewModel.moviesUiState,
                onMovieCardClick = { navController.navigate("movie_details/$it") },
                retryAction = moviesViewModel::getMovies
            )
        }
        composable(route = "movie_details/{movieId}") { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString("movieId").orEmpty()
            val movieDetailsViewModel: MovieDetailsViewModel by viewModel { parametersOf(movieId) }

            MovieDetailsScreen(
                movieDetailsUiState = movieDetailsViewModel.movieDetailsUiState,
                movieCast = movieDetailsViewModel.movieCst,
                retryAction = { movieDetailsViewModel.getMovieDetails(movieId) }
            )
        }
    }
}