package com.test.movies.network

import com.test.movies.model.MovieCredits
import com.test.movies.model.MovieDetails
import com.test.movies.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("trending/movie/day")
    suspend fun getMoviesOfTheDay(
        @Query("page") page: Int
    ): MovieList

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: String
    ): MovieDetails

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: String
    ): MovieCredits
}