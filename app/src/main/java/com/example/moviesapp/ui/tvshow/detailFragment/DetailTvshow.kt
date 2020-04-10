package com.example.moviesapp.ui.tvshow.detailFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.example.moviesapp.R
import com.example.moviesapp.backend.data.`object`.TVDetails
import com.example.moviesapp.backend.data.api.MovieDBClient
import com.example.moviesapp.backend.data.api.MovieDBInterface
import com.example.moviesapp.backend.data.api.POSTER_PATH
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.ui.tvshow.viewmodel.TVDetailViewModel
import kotlinx.android.synthetic.main.fragment_detail_movies.*
import kotlinx.android.synthetic.main.fragment_detail_tvshow.*

/**
 * A simple [Fragment] subclass.
 */
class DetailTvshow : Fragment() {

    private lateinit var detailViewModel : TVDetailViewModel
    private lateinit var tvDetailRepository: TVDetailRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TV ID - DIKIRIMKAN MELALUI INTENT
        val id  = arguments?.getInt("id", 1)
        Log.e("IDID", id.toString())
        // API SERVICE
        val api : MovieDBInterface = MovieDBClient.getClient()
        tvDetailRepository = TVDetailRepository(api)
        // VIEW MODEL
        detailViewModel = getViewModel(id!!)

        // BIND UI
        detailViewModel.tvDetails.observe(viewLifecycleOwner, Observer {
            bindUI(it)
        })

        // NETWORK STATE
        detailViewModel.networkState.observe(viewLifecycleOwner, Observer {
            // progress bar saat API diproses dan ketika selesai
            progress_detail_tv.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            // text ERROR saat gagal
            error_messages_detail_tvshow.visibility = if (it == NetworkState.ERROR)  View.VISIBLE else View.GONE
        })

    }

        // bind UI ke LAYOUT
        fun bindUI(items : TVDetails){
            tv_name_tvshow.text = items.name
            tv_score_tvshow.text = items.voteAverage.toString()
            tv_date_tvshow.text = items.firstAirDate
            tv_genre_tvshow.text = items.status
            tv_dec_tvshow.text = items.overview

            ratingBar_tvshow.progress = items.voteAverage.toInt()
            // bind poster using GLIDE
            val posterUrl = POSTER_PATH + items.posterPath
            Glide.with(this)
                .load(posterUrl)
                .into(tv_img_tvshow)
            val backdropUrl = POSTER_PATH + items.backdropPath
            Glide.with(this)
                .load(backdropUrl)
                .into(tv_poster_tvshow)
        }


    // get view model
    fun getViewModel(id: Int): TVDetailViewModel{
        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TVDetailViewModel(tvDetailRepository, id) as T
            }
        })[TVDetailViewModel::class.java]
    }
}
