package com.example.moviesapp.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviesapp.backend.data.`object`.ResultTV
import com.example.moviesapp.backend.data.api.MovieDBInterface
import com.example.moviesapp.backend.data.api.POST_PAGE
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.backend.data.repository.TVDataSource
import com.example.moviesapp.backend.data.repository.TVDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class TVListRepository(private val api: MovieDBInterface) {

    lateinit var tvPageList : LiveData<PagedList<ResultTV>> // live data halaman pagination
    lateinit var tvDataSourceFactory : TVDataSourceFactory // data factory

    // fetch data
    fun fetchMovieList(compositeDisposable: CompositeDisposable): LiveData<PagedList<ResultTV>> {

        tvDataSourceFactory = TVDataSourceFactory(api, compositeDisposable) // inisialisasi data factory

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE) // set max item per page
            .build()

        //inisialisai movie Page List
        tvPageList = LivePagedListBuilder(tvDataSourceFactory, config).build() // build
        return tvPageList
    }

    // network state
    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<TVDataSource, NetworkState>(
            tvDataSourceFactory.tvLiveDataSource, TVDataSource::networkState
        )
    }

}