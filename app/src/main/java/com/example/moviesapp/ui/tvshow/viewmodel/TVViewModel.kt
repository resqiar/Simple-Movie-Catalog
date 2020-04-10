package com.example.moviesapp.ui.tvshow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviesapp.backend.data.`object`.ResultTV
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.ui.tvshow.TVListRepository
import io.reactivex.disposables.CompositeDisposable

class TVViewModel(private val tvRepository : TVListRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val tvPagedList : LiveData<PagedList<ResultTV>> = tvRepository.fetchMovieList(compositeDisposable)
    val networkState : LiveData<NetworkState> = tvRepository.getNetworkState()

    fun isEmpty():Boolean{ // jika halaman kosong
        return tvPagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose() // dispose
    }

}