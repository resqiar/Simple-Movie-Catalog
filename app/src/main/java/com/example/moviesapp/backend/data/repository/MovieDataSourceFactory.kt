package com.example.moviesapp.backend.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviesapp.backend.data.`object`.Result
import com.example.moviesapp.backend.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
        private val api : MovieDBInterface,
        private val compositeDisposable: CompositeDisposable
    ): DataSource.Factory<Int, Result>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()   // movie data source // live data

    override fun create(): DataSource<Int, Result> {
        val movieDataSource = MovieDataSource(api, compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}