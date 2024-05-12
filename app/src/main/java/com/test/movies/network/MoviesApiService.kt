package com.test.movies.network

import com.test.movies.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    @GET("trending/movie/day")
    suspend fun getMoviesOfTheDay(): MovieList

    @GET("trending/movie/day")
    suspend fun getMoviesOfTheDay(
        @Query("page") page: Int
    ): MovieList
}
