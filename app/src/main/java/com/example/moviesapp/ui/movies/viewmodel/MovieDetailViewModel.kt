package com.example.moviesapp.ui.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.backend.data.`object`.MovieDetails
import com.example.moviesapp.backend.data.repository.MovieDetailDataSource
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.ui.movies.detailfragment.MovieDetailRepository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel(private val movieRepository: MovieDetailRepository, id : Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable() // composite disposable

    val movieDetails : LiveData<MovieDetails> = movieRepository.fetchMovieDetail(compositeDisposable, id)
    val networkState : LiveData<NetworkState> = movieRepository.getMovieDetailNetworkState()

        // dungsi ini dipanggil ketika aktivity/fragment sudah tidak berjalan
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()   // dispose thread
    }

}