package com.example.moviesapp.backend.data.api

import io.reactivex.plugins.RxJavaPlugins
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

// link yang akan digunakan
// https://api.themoviedb.org/3/discover/movie?api_key=8fe4065393486ed1cb8eb8ac2c87a319 // list movies URL
// https://api.themoviedb.org/3/movie/76285?api_key=8fe4065393486ed1cb8eb8ac2c87a319&language=en-US // get Details by ID
// https://api.themoviedb.org/3/    // base URL
// https://image.tmdb.org/t/p/w185/  // poster path

    const val API_KEY = "8fe4065393486ed1cb8eb8ac2c87a319"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POSTER_PATH = "https://image.tmdb.org/t/p/w780"

    const val FIRST_PAGE = 1
    const val POST_PAGE = 21

object MovieDBClient {

    fun getClient( ): MovieDBInterface {
        val requestInterceptor = Interceptor{chain ->  

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)   // return nilai
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // 30 detik jika tidak selesai maka akan dikirim status ERROR
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDBInterface::class.java)
    }

}