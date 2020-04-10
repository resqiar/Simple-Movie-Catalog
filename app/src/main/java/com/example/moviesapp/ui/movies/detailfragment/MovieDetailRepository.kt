package com.example.moviesapp.ui.movies.detailfragment

import androidx.lifecycle.LiveData
import com.example.moviesapp.backend.data.`object`.MovieDetails
import com.example.moviesapp.backend.data.api.MovieDBInterface
import com.example.moviesapp.backend.data.repository.MovieDetailDataSource
import com.example.moviesapp.backend.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailRepository(private val api : MovieDBInterface) {

        // dari repository
    lateinit var movieDetailDataSource: MovieDetailDataSource

    fun fetchMovieDetail(compositeDisposable: CompositeDisposable, id : Int): LiveData<MovieDetails> {

        // init data source
        movieDetailDataSource = MovieDetailDataSource(api, compositeDisposable)
        // fetch id
        movieDetailDataSource.fetchMovieDetail(id)

        // return
        return movieDetailDataSource.movieDetailResponse
    }

    fun getMovieDetailNetworkState(): LiveData<NetworkState>{
        return movieDetailDataSource.networkState
    }
}