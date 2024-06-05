package com.test.movies.ui.screens.movieDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.movies.R
import com.test.movies.model.Actor
import com.test.movies.model.MovieDetails
import com.test.movies.ui.commonElements.Poster
import com.test.movies.ui.screens.moviesList.ErrorScreen
import com.test.movies.ui.screens.moviesList.LoadingScreen
import kotlin.math.round

@Composable
fun MovieDetailsScreen(
    movieDetailsUiState: UiState<MovieDetails>,
    movieCast: List<Actor>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    when (movieDetailsUiState) {
        is UiState.Success<MovieDetails> ->
            MovieDetailsColumn(
                movieDetailsUiState.data,
                movieCast,
                modifier = modifier.fillMaxSize()
            )

        UiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
        UiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun MovieDetailsColumn(
    movieDetails: MovieDetails,
    movieCast: List<Actor>,
    modifier: Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MovieBackgroundPoster(
            movieDetails.backdropPath
        )

        MovieDetails(
            movieDetails,
            movieCast
        )
    }
}

@Composable
fun MovieBackgroundPoster(
    imagePath: String?,
    modifier: Modifier = Modifier
) {
    Poster(posterPath = imagePath ?: "",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
            .drawWithContent {
                val colors = listOf(
                    Color.Black,
                    Color.Transparent
                )
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(colors),
                    blendMode = BlendMode.DstIn
                )
            }
    )
}

@Composable
fun MovieDetails(
    movieDetails: MovieDetails,
    movieCast: List<Actor>,
    modifier: Modifier = Modifier
) {
    val genres = movieDetails.genres.map { it.name }.joinToString(", ")

    Column(modifier.padding(start = 16.dp, end = 16.dp)) {

        Text(
            text = movieDetails.title,
            fontFamily = FontFamily.Monospace,
            lineHeight = 50.sp,
            fontSize = 40.sp,
            textAlign = TextAlign.Left
        )

        Text(
            text = genres,
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.coral),
            modifier = Modifier.padding(top = 8.dp)
        )

        StarRatingBar(
            maxStars = 5,
            rating = round(movieDetails.voteAverage / 2),
            voteCount = movieDetails.voteCount,
            modifier = Modifier.padding(top = 8.dp)
        )

        Column(modifier = Modifier.padding(top = 20.dp)) {
            Text(
                text = "Storyline",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = movieDetails.overview,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Cast(movieCast)
    }
}


@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Double,
    voteCount: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = Icons.Rounded.Star
            val iconTintColor =
                if (isSelected) colorResource(id = R.color.coral) else colorResource(id = R.color.grey_80)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = modifier.size(8.dp))

        Text(
            text = "$voteCount REVIEWS",
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.sp,
            fontSize = 14.sp,
            color = colorResource(id = R.color.grey_80)
        )
    }
}

@Composable
fun Cast(
    cast: List<Actor>,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(top = 20.dp)) {
        Text(
            text = "Cast",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(items = cast, key = { actor -> actor.id }) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .width(100.dp)
                        .height(190.dp)
                        .padding(end = 8.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Poster(
                            posterPath = it.profilePath ?: "",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}