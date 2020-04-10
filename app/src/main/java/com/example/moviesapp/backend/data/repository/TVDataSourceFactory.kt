package com.example.moviesapp.backend.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviesapp.backend.data.`object`.ResultTV
import com.example.moviesapp.backend.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable

class TVDataSourceFactory(private val api : MovieDBInterface, private val compositeDisposable: CompositeDisposable):
    DataSource.Factory<Int, ResultTV>() {

    val tvLiveDataSource = MutableLiveData<TVDataSource>()

    override fun create(): DataSource<Int, ResultTV> {
        val tvDataSource = TVDataSource(api, compositeDisposable)
        tvLiveDataSource.postValue(tvDataSource)
        return tvDataSource
    }
}