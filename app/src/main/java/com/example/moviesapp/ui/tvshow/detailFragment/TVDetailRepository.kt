package com.example.moviesapp.ui.tvshow.detailFragment

import androidx.lifecycle.LiveData
import com.example.moviesapp.backend.data.`object`.TVDetails
import com.example.moviesapp.backend.data.api.MovieDBInterface
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.backend.data.repository.TVDetailDataSource
import io.reactivex.disposables.CompositeDisposable

class TVDetailRepository(private val api: MovieDBInterface) {

    // dari repository
    lateinit var tvDetailDataSource: TVDetailDataSource

    fun fetchTVDetail(compositeDisposable: CompositeDisposable, id : Int): LiveData<TVDetails> {

        // init data source
        tvDetailDataSource = TVDetailDataSource(api, compositeDisposable)
        // fetch id
        tvDetailDataSource.fetchTVDetail(id)

        // return
        return tvDetailDataSource.tvDetailResponse
    }

    fun getTVDetailNetworkState(): LiveData<NetworkState>{
        return tvDetailDataSource.networkState
    }
}