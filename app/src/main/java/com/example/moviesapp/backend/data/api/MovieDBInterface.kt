package com.example.moviesapp.backend.data.api

import com.example.moviesapp.backend.data.`object`.ListMovies
import com.example.moviesapp.backend.data.`object`.ListTV
import com.example.moviesapp.backend.data.`object`.MovieDetails
import com.example.moviesapp.backend.data.`object`.TVDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {

    // link yang akan digunakan
    // https://api.themoviedb.org/3/movie/popular?api_key=8fe4065393486ed1cb8eb8ac2c87a319&language=en-US&page=1 // list movies URL
    // https://api.themoviedb.org/3/movie/76285?api_key=8fe4065393486ed1cb8eb8ac2c87a319&language=en-US // get Details by ID

    // https://api.themoviedb.org/3/tv/popular?api_key=8fe4065393486ed1cb8eb8ac2c87a319&language=en-US&page=1   // list tv show
    // https://api.themoviedb.org/3/tv/popular?api_key=8fe4065393486ed1cb8eb8ac2c87a319&language=en-US&page=1
    // https://api.themoviedb.org/3/    // base URL

    // M O V I E L I S T - POPULAR MOVIES
    @GET("movie/popular")
    fun getMovie(@Query("page") page : Int): Single<ListMovies>

    // M O V I E  D E T A I L S - execute by id
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    //T V S H O W L I S T
    @GET("tv/popular")
    fun getTV(@Query("page")page : Int): Single<ListTV>

    // T V  D E T A I L S - execute by id
    @GET("tv/{tv_id}")
    fun getTVDetails(@Path("tv_id") id: Int): Single<TVDetails>

}