package com.example.moviesapp.ui.tvshow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.backend.data.`object`.TVDetails
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.ui.tvshow.detailFragment.TVDetailRepository
import io.reactivex.disposables.CompositeDisposable

class TVDetailViewModel(private val tvRepository : TVDetailRepository, id : Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val tvDetails : LiveData<TVDetails> = tvRepository.fetchTVDetail(compositeDisposable, id)
    val networkState : LiveData<NetworkState> = tvRepository.getTVDetailNetworkState()

    // dungsi ini dipanggil ketika aktivity/fragment sudah tidak berjalan
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()   // dispose thread
    }
}