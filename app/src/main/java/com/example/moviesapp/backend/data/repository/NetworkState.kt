package com.example.moviesapp.backend.data.repository


    enum class Status{  // proses API yg berjalan
        RUNNING,
        SUCCESS,
        FAILED
    }

class NetworkState(val status : Status, val message : String) {

    companion object{
        val LOADED : NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING : NetworkState  = NetworkState(Status.RUNNING, message = "Running")
        val ERROR : NetworkState = NetworkState(Status.FAILED, message = "Something went wrong, try again later!")
        val EMPTY : NetworkState = NetworkState(Status.FAILED, message = "You have reached the end")
    }
}