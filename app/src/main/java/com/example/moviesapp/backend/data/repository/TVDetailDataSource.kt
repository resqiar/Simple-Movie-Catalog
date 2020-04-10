package com.example.moviesapp.backend.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.backend.data.`object`.TVDetails
import com.example.moviesapp.backend.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TVDetailDataSource (private val api : MovieDBInterface, private val compositeDisposable: CompositeDisposable){
    // N E T W O R K    S T A T E
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>
        get() = _networkState // dengan get, tidak perlu setter getter

    // T V    D E T A I L
    private val _tvDetailResponse = MutableLiveData<TVDetails>()
    val tvDetailResponse : LiveData<TVDetails>
        get() = _tvDetailResponse // dengan get, tidak perlu setter getter


    // F E T C H    D A T A
    fun fetchTVDetail(id : Int){
        _networkState.postValue(NetworkState.LOADING)   // loading ... ...

        try {
            compositeDisposable.add(    // disposable
                api.getTVDetails(id)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        // parameter ketika sukses
                        {
                            _tvDetailResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)    // status selesai di LOAD
                        },
                        // parameter ketika gagal
                        {
                            _networkState.postValue(NetworkState.ERROR) // status gagal
                            Log.e("fetchTVDetail", it.message.toString())
                        }
                    )
            )
        }catch (e: Exception){
            Log.e("fetchTVDetail", e.message.toString())
        }
    }
}