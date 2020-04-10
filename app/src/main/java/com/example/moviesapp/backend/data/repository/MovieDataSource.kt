package com.example.moviesapp.backend.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviesapp.backend.data.`object`.ListMovies
import com.example.moviesapp.backend.data.`object`.Result
import com.example.moviesapp.backend.data.api.FIRST_PAGE
import com.example.moviesapp.backend.data.api.MovieDBInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val api : MovieDBInterface, private val compositeDisposable: CompositeDisposable): PageKeyedDataSource<Int, Result>() { // load current data

    private var page = FIRST_PAGE // halaman awal
    val networkState : MutableLiveData<NetworkState> = MutableLiveData()    // network state

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) { // load current data
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            api.getMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.results, null, page+1)
                    networkState.postValue(NetworkState.LOADED)
                },{
                    networkState.postValue(NetworkState.ERROR)  // error
                    Log.e("LoadInitialMovie", it.message.toString())
                })
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {  // load after page +1
        compositeDisposable.add(
            api.getMovie(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // cek apakah masih tersisa halaman yang akan di load
                    if (it.totalPages >= params.key){
                        callback.onResult(it.results, params.key + 1)   // load halaman selanjutnya
                        networkState.postValue(NetworkState.LOADED)
                    }else{  // jika halaman sudah habis
                        networkState.postValue(NetworkState.EMPTY)
                    }
                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("LoadAfterMovie", it.message.toString())
                })


        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) { // load bego page

    }


}