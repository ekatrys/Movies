package com.test.movies

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.test.movies.data.MoviesRepository
import com.test.movies.data.NetworkMoviesRepository
import com.test.movies.network.MoviesApiService
import com.test.movies.ui.screens.movieDetails.MovieDetailsViewModel
import com.test.movies.ui.screens.moviesList.MovieListViewModel
import com.test.movies.until.Constants
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .build().create(MoviesApiService::class.java)
    }

    single<MoviesRepository> { NetworkMoviesRepository(get()) }
    viewModel<MovieListViewModel> {
        MovieListViewModel(get())
    }

    viewModel<MovieDetailsViewModel> { (movieId: String) ->
        MovieDetailsViewModel(movieId, get())
    }
}
private val json = Json { ignoreUnknownKeys = true }

private val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${Constants.TOKEN}")
            .header("Content-Type", "application/json")
            .build()
        chain.proceed(newRequest)
    }
    .build()