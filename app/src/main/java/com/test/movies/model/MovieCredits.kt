package com.test.movies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCredits(
    val cast: List<Actor>
)

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePath: String?
)