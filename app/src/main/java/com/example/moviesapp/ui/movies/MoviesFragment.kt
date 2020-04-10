package com.example.moviesapp.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.backend.data.`object`.MovieDetails
import com.example.moviesapp.backend.data.api.MovieDBClient
import com.example.moviesapp.backend.data.api.MovieDBInterface
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.ui.movies.adapter.MovieAdapter
import com.example.moviesapp.ui.movies.detailfragment.DetailMovies
import com.example.moviesapp.ui.movies.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.network_state_item.*

class MoviesFragment : Fragment() {

    // view model
    private lateinit var viewModel : MovieViewModel
    // pagination
    lateinit var movieListRepository: MovieListRepository

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

        // API SERVICE
        val api : MovieDBInterface = MovieDBClient.getClient()
        movieListRepository = MovieListRepository(api)

        // VIEW MODEL
        viewModel = getViewModel()

        // SHOW RECYCLERVIEW
        val movieAdapter = context?.let { MovieAdapter(it) }
        val gridLayoutManager = GridLayoutManager(context, 3)
        // modify span
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter?.getItemViewType(position)
                return if (viewType == movieAdapter?.MOVIE_TYPE) 1 else 3 // jika MOVIE_TYPE maka occupy menjadi 1 baris
            }
        }
        // set adapter
        rv_movies.layoutManager = gridLayoutManager
        rv_movies.adapter = movieAdapter
        movieAdapter?.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback{
            override fun onItemClicked(id: Int?) {

                //siapkan data parcelable
                val fragGet = DetailMovies()
                val bundle = Bundle()

                // masukkan ke bundle
                if (id != null) {
                    bundle.putInt("id", id)
                }
                fragGet.arguments = bundle

                // buat perpindahan fragment
                val mFragment = fragmentManager
                mFragment?.beginTransaction()?.apply {
                    add(R.id.nav_host_fragment, fragGet, MovieDetails::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }

        })


        // VIEW MODEL
        viewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            movieAdapter?.submitList(it)
        })
        // network state
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            progressBar_movie.visibility = if (viewModel.isEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            error_messages_movies.visibility = if ( viewModel.isEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.isEmpty()){
                movieAdapter?.setNetworkState(it)
            }

        })
    }


        // get view model
        fun getViewModel(): MovieViewModel{
            return ViewModelProvider(this, object : ViewModelProvider.Factory{
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return MovieViewModel(movieListRepository) as T
                }
            })[MovieViewModel::class.java]
        }
}
