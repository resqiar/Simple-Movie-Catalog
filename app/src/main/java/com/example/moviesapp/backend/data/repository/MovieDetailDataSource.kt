package com.example.moviesapp.backend.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.backend.data.`object`.MovieDetails
import com.example.moviesapp.backend.data.api.MovieDBInterface
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailDataSource (private val api : MovieDBInterface, private val compositeDisposable: CompositeDisposable) { // composite disposable membuat memori tidak bocor karena setelah activity/fragment selesai, maka langsung di destroy

    // N E T W O R K    S T A T E
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>
        get() = _networkState // dengan get, tidak perlu setter getter

    // M O V I E    D E T A I L
    private val _movieDetailResponse = MutableLiveData<MovieDetails>()
    val movieDetailResponse : LiveData<MovieDetails>
        get() = _movieDetailResponse // dengan get, tidak perlu setter getter


    // F E T C H    D A T A
    fun fetchMovieDetail(id : Int){
        _networkState.postValue(NetworkState.LOADING)   // loading ... ...

        try {
            compositeDisposable.add(    // disposable
                api.getMovieDetails(id)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            // parameter ketika sukses
                        {
                            _movieDetailResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)    // status selesai di LOAD
                        },
                            // parameter ketika gagal
                        {
                            _networkState.postValue(NetworkState.ERROR) // status gagal
                            Log.e("fetchMovieDetail", it.message.toString())
                        }
                    )
            )
        }catch (e: Exception){
            Log.e("fetchMovieDetail", e.message.toString())
        }
    }


}