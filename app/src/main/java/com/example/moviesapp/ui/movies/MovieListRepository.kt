package com.example.moviesapp.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviesapp.backend.data.`object`.Result
import com.example.moviesapp.backend.data.api.MovieDBInterface
import com.example.moviesapp.backend.data.api.POST_PAGE
import com.example.moviesapp.backend.data.repository.MovieDataSource
import com.example.moviesapp.backend.data.repository.MovieDataSourceFactory
import com.example.moviesapp.backend.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.Transformations.switchMap as switchMap1

class MovieListRepository(private val api: MovieDBInterface) {

    lateinit var moviePageList : LiveData<PagedList<Result>> // live data halaman pagination
    lateinit var movieDataSourceFactory : MovieDataSourceFactory // data factory

    fun fetchMovieList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Result>> {

        movieDataSourceFactory = MovieDataSourceFactory(api, compositeDisposable) // inisialisasi data factory

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE) // set max item per page
            .build()

        //inisialisai movie Page List
        moviePageList = LivePagedListBuilder(movieDataSourceFactory, config).build() // build
        return moviePageList
    }

    // network state
    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }

}