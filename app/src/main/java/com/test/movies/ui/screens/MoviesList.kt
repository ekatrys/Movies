package com.test.movies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.test.movies.R
import com.test.movies.model.Movie

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Image(painter = painterResource(id = R.drawable.movie_card), contentDescription = "")
        Text(text = movie.title)
    }

}

@Preview
@Composable
fun MovieCardPreview() {
    MovieCard(testMovie)
}

val testMovie = Movie(
    id = 934632,
    posterPath = "/cxevDYdeFkiixRShbObdwAHBZry.jpg",
    adult = false,
    title = "Rebel Moon - Part Two: The Scargiver",
    genreIds = listOf(
        878,
        28,
        18,
        10770
    ),
    voteAverage = 6.132,
    voteCount = 272
)