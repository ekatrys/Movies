package com.test.movies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.movies.R
import com.test.movies.data.BASE_IMAGE_URL
import com.test.movies.model.Movie
import com.test.movies.ui.theme.MoviesTheme

@Composable
fun HomeScreen(
    moviesUiState: MoviesUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (moviesUiState) {
        is MoviesUiState.Success -> MoviesGrid(
            moviesUiState.moves,
            modifier = modifier.fillMaxWidth()
        )

        MoviesUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
        MoviesUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun MoviesGrid(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(4.dp),
        contentPadding = contentPadding
    ) {
        items(items = movies, key = { movie -> movie.id }) { movie ->
            MoviePoster(
                movie,
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
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(BASE_IMAGE_URL + "w300" + movie.posterPath)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
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
        MoviesGrid(mockData)
    }
}