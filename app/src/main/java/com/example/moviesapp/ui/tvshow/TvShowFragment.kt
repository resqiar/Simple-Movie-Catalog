package com.example.moviesapp.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.backend.data.api.MovieDBClient
import com.example.moviesapp.backend.data.api.MovieDBInterface
import com.example.moviesapp.backend.data.repository.NetworkState
import com.example.moviesapp.ui.tvshow.adapter.TVAdapter
import com.example.moviesapp.ui.tvshow.viewmodel.TVViewModel
import kotlinx.android.synthetic.main.fragment_tvshow.*

class TvShowFragment : Fragment() {

    // view model
    private lateinit var viewModel: TVViewModel
    // pagination
    lateinit var tvListRepository: TVListRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_tvshow.setHasFixedSize(true)

        // API SERVICE
        val api: MovieDBInterface = MovieDBClient.getClient()
        tvListRepository = TVListRepository(api)

        // VIEW MODEL
        viewModel = getViewModel()

        // SHOW RECYCLERVIEW
        val tvAdapter = context?.let { TVAdapter(it) }
        val gridLayoutManager = GridLayoutManager(context, 3)
        // modify span
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = tvAdapter?.getItemViewType(position)
                return if (viewType == tvAdapter?.TV_TYPE) 1 else 3 // jika MOVIE_TYPE maka occupy menjadi 1 baris
            }
        }
        // set adapter
        rv_tvshow.layoutManager = gridLayoutManager
        rv_tvshow.adapter = tvAdapter
        tvAdapter?.setOnItemClickCallback(object : TVAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int?) {

                //siapkan data
                val bundle = Bundle()

                // masukkan ke bundle
                if (id != null) {
                    bundle.putInt("id", id)
                }

                // buat perpindahan fragment
                view.findNavController()
                    .navigate(R.id.action_navigation_tvshow_to_navigation_tvshow_detail, bundle)
            }

        })


        // VIEW MODEL
        viewModel.tvPagedList.observe(viewLifecycleOwner, Observer {
            tvAdapter?.submitList(it)
        })
        // network state
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            progressBar_tv.visibility =
                if (viewModel.isEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            error_messages_tv.visibility =
                if (viewModel.isEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.isEmpty()) {
                tvAdapter?.setNetworkState(it)
            }

        })
    }


    // get view model
    fun getViewModel(): TVViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TVViewModel(tvListRepository) as T
            }
        })[TVViewModel::class.java]
    }
}
