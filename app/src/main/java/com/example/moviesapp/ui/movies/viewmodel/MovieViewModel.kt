package com.example.moviesapp.ui.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviesapp.backend.data.`object`.Result
import com.example.moviesapp.backend.data.api.MovieDBInterface
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.ui.movies.MovieListRepository
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(private val movieRepository: MovieListRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Result>> = movieRepository.fetchMovieList(compositeDisposable)
    val networkState : LiveData<NetworkState> = movieRepository.getNetworkState()

    fun isEmpty():Boolean{ // jika halaman kosong
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}