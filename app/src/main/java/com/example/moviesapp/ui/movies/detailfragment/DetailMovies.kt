package com.example.moviesapp.ui.movies.detailfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.example.moviesapp.R
import com.example.moviesapp.backend.data.`object`.MovieDetails
import com.example.moviesapp.backend.data.api.MovieDBClient
import com.example.moviesapp.backend.data.api.MovieDBInterface
import com.example.moviesapp.backend.data.api.POSTER_PATH
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.ui.movies.viewmodel.MovieDetailViewModel
import kotlinx.android.synthetic.main.fragment_detail_movies.*
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 */
class DetailMovies : Fragment() {

    private lateinit var detailViewModel : MovieDetailViewModel
    private lateinit var movieDetailRepository: MovieDetailRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MOVIE ID - DIKIRIMKAN MELALUI INTENT
        val id  = arguments?.getInt("id", 1)

            // API SERVICE
        val api : MovieDBInterface = MovieDBClient.getClient()
        movieDetailRepository = MovieDetailRepository(api)
            // VIEW MODEL
        detailViewModel = getViewModel(id!!)

        detailViewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            bindUI(it)
        })
            // NETWORK STATE
        detailViewModel.networkState.observe(viewLifecycleOwner, Observer {
                // progress bar saat API diproses dan ketika selesai
            progress_detail.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
                // text ERROR saat gagal
            error_messages_detail.visibility = if (it == NetworkState.ERROR)  View.VISIBLE else View.GONE
        })
    }
        // bind UI ke LAYOUT
        fun bindUI(items : MovieDetails){
            tv_name_movie.text = items.title
            tv_score_movie.text = items.rating.toString()
            tv_date_movie.text = items.releaseDate
            tv_genre_movie.text = items.status
            tv_dec_movie.text = items.overview

            ratingBar_movie.progress = items.rating.toInt()
            // bind poster using GLIDE
            val posterUrl = POSTER_PATH + items.posterPath
            Glide.with(this)
                .load(posterUrl)
                .into(tv_img_movie)
            val backdropUrl = POSTER_PATH + items.backdropPath
            Glide.with(this)
                .load(backdropUrl)
                .into(tv_poster_movie)
        }

        // get view model
        fun getViewModel(id: Int): MovieDetailViewModel{
            return ViewModelProvider(this, object : ViewModelProvider.Factory{
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return MovieDetailViewModel(movieDetailRepository, id) as T
                }
            })[MovieDetailViewModel::class.java]
        }
}
