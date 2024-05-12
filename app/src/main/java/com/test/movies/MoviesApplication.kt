package com.test.movies

import android.app.Application
import com.test.movies.data.AppContainer
import com.test.movies.data.DefaultAppContainer

class MoviesApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}