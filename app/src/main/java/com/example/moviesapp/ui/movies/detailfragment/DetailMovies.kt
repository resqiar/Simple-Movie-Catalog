package com.example.moviesapp.ui.movies.detailfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.moviesapp.R
import com.example.moviesapp.ui.movies.Movie
import kotlinx.android.synthetic.main.fragment_detail_movies.*

/**
 * A simple [Fragment] subclass.
 */
class DetailMovies : Fragment() {

    companion object {
        const val DATA_DETAIL_MOVIE = "data_detail_movie"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // tangkap datanya
        val data = arguments?.getParcelable<Movie>("data_detail_movie")

        if (data != null) {
            tv_name_movie.text = data.movieName
            tv_date_movie.text = data.movieDate
            tv_dec_movie.text = data.movieDec
            tv_genre_movie.text = data.movieGenre
            tv_score_movie.text = data.movieScore + " Scores"
            tv_img_movie.setImageResource(data.moviePhoto)
        }

        // btn watchlist
        btn_watchlist.setOnClickListener {
            Toast.makeText(context, data?.movieName + " " + getString(R.string.btn_add_watchlist), Toast.LENGTH_SHORT).show()
        }

    }

}
