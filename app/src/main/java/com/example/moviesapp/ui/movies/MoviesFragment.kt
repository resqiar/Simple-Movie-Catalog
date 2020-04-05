package com.example.moviesapp.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.ui.movies.adapter.MoviesAdapter
import com.example.moviesapp.ui.movies.detailfragment.DetailMovies
import com.example.moviesapp.ui.movies.detailfragment.DetailMovies.Companion.DATA_DETAIL_MOVIE
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {
    private val movie = ArrayList<Movie>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_movies.setHasFixedSize(true)
        // panggil fungsi getListMovies
        movie.addAll(getListMovies())
        //panggil fungsi Grid Recycle
        showRecyclerGrid()
        // tambah listener ke setiap item recycler
    }

    // fungsi mengambil daftar movies dari array
    private fun getListMovies(): ArrayList<Movie>{
        val dataName = resources.getStringArray(R.array.data_name_movies)
        val dataDate = resources.getStringArray(R.array.data_date_movies)
        val dataScore = resources.getStringArray(R.array.data_score_movies)
        val dataGenre = resources.getStringArray(R.array.data_genre_movies)
        val dataDec = resources.getStringArray(R.array.data_dec_movies)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo_movies)

        val listMovie = ArrayList<Movie>()
        for(position in dataName.indices){
            val movie = Movie(
                dataName[position],
                dataDate[position],
                dataScore[position],
                dataGenre[position],
                dataDec[position],
                dataPhoto.getResourceId(position, -1)
            )
            listMovie.add(movie)
        }

        dataPhoto.recycle()
        return listMovie
    }

    private fun showRecyclerGrid() {
        rv_movies.layoutManager = GridLayoutManager(context, 3)
        val gridHeroAdapter = MoviesAdapter(movie)
        rv_movies.adapter = gridHeroAdapter

        gridHeroAdapter.setOnItemClickCallback(object : MoviesAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {

                // masukkan data
                val fragGet = DetailMovies()
                val bundle = Bundle()

                bundle.putParcelable(DATA_DETAIL_MOVIE, data)
                fragGet.arguments = bundle


                // buat intent
                val mFragment = fragmentManager
                mFragment?.beginTransaction()?.apply {
                    add(R.id.nav_host_fragment, fragGet, DetailMovies::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }


            }
        })

    }
}
