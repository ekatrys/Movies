package com.test.movies.data

import com.test.movies.model.MovieCredits
import com.test.movies.model.MovieDetails
import com.test.movies.model.MovieList
import com.test.movies.network.MoviesApiService

interface MoviesRepository {
    suspend fun getMoviesOfTheDay(curPage: Int): MovieList
    suspend fun getMovieDetails(movieId: String): MovieDetails
    suspend fun getMovieCredits(movieId: String): MovieCredits
}

class NetworkMoviesRepository(private val retrofitService: MoviesApiService) : MoviesRepository {
    override suspend fun getMoviesOfTheDay(curPage: Int): MovieList {
        return retrofitService.getMoviesOfTheDay(curPage)
    }

    override suspend fun getMovieDetails(movieId: String): MovieDetails {
        return retrofitService.getMovieDetails(movieId)
    }

    override suspend fun getMovieCredits(movieId: String): MovieCredits {
        return retrofitService.getMovieCredits(movieId)
    }
}