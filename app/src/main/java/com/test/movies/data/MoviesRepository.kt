package com.test.movies.data

import com.test.movies.model.MovieList
import com.test.movies.network.MoviesApiService

interface MoviesRepository {
    suspend fun getMoviesOfTheDay(): MovieList
}

class NetworkMoviesRepository(private val retrofitService: MoviesApiService) : MoviesRepository {
    override suspend fun getMoviesOfTheDay(): MovieList {
        return retrofitService.getMoviesOfTheDay()
    }
}