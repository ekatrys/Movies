package com.test.movies.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.test.movies.network.MoviesApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"

interface AppContainer {
    val moviesRepository: MoviesRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNDk1NjQ2ZDU5YjFjNzBmYzdiZjc0N2EwZDFlMDc0MSIsInN1YiI6IjYyYWI4YjhmZDRkNTA5MDA1MTUwZTM5ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ho2AOiYlMkzbOA59y3rX0i3re3bQPCyEY7FD48m6f1g"

    private val json = Json { ignoreUnknownKeys = true }

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $TOKEN")
                .header("Content-Type", "application/json")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    private val retrofit = Retrofit
        .Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    private val retrofitService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }

    override val moviesRepository: MoviesRepository
        get() = NetworkMoviesRepository(retrofitService)
}