package com.test.movies.ui.screens.moviesList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.movies.R
import com.test.movies.model.Movie
import com.test.movies.ui.commonElemenets.Poster
import com.test.movies.ui.screens.movieDetails.UiState
import com.test.movies.ui.theme.MoviesTheme

@Composable
fun MoviesScreen(
    moviesUiState: UiState<List<Movie>>,
    retryAction: () -> Unit,
    onMovieCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (moviesUiState) {
        is UiState.Success -> MoviesGrid(
            moviesUiState.data,
            onMovieCardClick,
            modifier = modifier.fillMaxWidth()
        )

        UiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
        UiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun MoviesGrid(
    movies: List<Movie>,
    onMovieCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp)
) {
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(4.dp),
        state = listState,
        contentPadding = contentPadding
    ) {
        items(items = movies, key = { movie -> movie.id }) { movie ->
            MoviePoster(
                movie,
                onMovieCardClick,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .aspectRatio(0.7f)
            )
        }
    }
}

@Composable
fun MoviePoster(
    movie: Movie,
    onMovieCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onMovieCardClick(movie.id) },
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Poster(
            movie.posterPath,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Preview(showBackground = true)
@Composable
fun MoviesGridScreenPreview() {
    MoviesTheme {
        val mockData = List(10) {
            Movie(
                it,
                "Love Lies Bleeding",
                false,
                "/p7jyFWiLyHPttqYBFAlLJwtYTYH.jpg",
                listOf(80, 10749, 53),
                7.449,
                49
            )
        }
        MoviesGrid(mockData, {})
    }
}