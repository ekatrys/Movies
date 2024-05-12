package com.test.movies.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieList(
    val page: Int,
    val results: List<Movie>
)
