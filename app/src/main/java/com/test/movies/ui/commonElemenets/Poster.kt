package com.test.movies.ui.commonElemenets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.movies.R
import com.test.movies.until.Constants

@Composable
fun Poster(
    posterPath: String,
    contentScale: ContentScale? = null,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(Constants.BASE_IMAGE_URL + "w300" + posterPath)
            .crossfade(true)
            .build(),
        contentScale = contentScale ?: ContentScale.Fit,
        contentDescription = "",
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
        modifier = modifier

    )
}