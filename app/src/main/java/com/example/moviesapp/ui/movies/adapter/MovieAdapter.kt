package com.example.moviesapp.ui.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.backend.data.`object`.Result
import com.example.moviesapp.backend.data.api.POSTER_PATH
import com.example.moviesapp.backend.data.repository.NetworkState
import kotlinx.android.synthetic.main.item_row_movie.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class MovieAdapter(val context: Context) :
    PagedListAdapter<Result, RecyclerView.ViewHolder>(DiffCallback()) {

    // dua jenis variable yg akan dipilih ( recyclerview / network state )
    val MOVIE_TYPE = 1
    val NETWORK_TYPE = 2
    private var networkState: NetworkState? = null

    // constructor butuh class DIFF UTIl
    class DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        // jika MOVIE_TYPE maka inflate recyclerview item
        if (viewType == MOVIE_TYPE) {
            view = layoutInflater.inflate(R.layout.item_row_movie, parent, false)
            return MovieViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_TYPE) {
            (holder as MovieViewHolder).bind(getItem(position), context)
        } else
            (holder as NetworkStateItemViewHolder).bind(networkState)
    }

    // cek apakah masih ada baris baru
    private fun extraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (extraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {  // tipe yg akan di inflate
        return if (extraRow() && position == itemCount - 1) {
            NETWORK_TYPE
        } else {
            MOVIE_TYPE
        }
    }

    // inisialisasi networkState
    fun setNetworkState(nNetworkState: NetworkState) {
        // current Network State
        val previousState = this.networkState
        val hadExtraRow = extraRow()
        // new Network state
        this.networkState = nNetworkState
        val hasExtraRow = extraRow()

        // cek extra row
        if (hadExtraRow != hasExtraRow) {
            when {
                hadExtraRow -> notifyItemRemoved(super.getItemCount())
                hasExtraRow -> notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != nNetworkState) { // hasExtraRow dan hadExtraRow sama // ERROR
            notifyItemChanged(itemCount - 1)
        }
    }

    // VIEW HOLDER CLASS
    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Result?, context: Context) {
            itemView.movie_name.text = movie?.title
            itemView.movie_date.text = movie?.releaseDate

            //poster url
            val posterUrl = POSTER_PATH + movie?.posterPath
            // bind poster
            Glide.with(itemView.context)
                .load(posterUrl)
                .into(itemView.movie_img)

            itemView.setOnClickListener {
                // buat onclick listener yang akan menuju detail Fragment
                onItemClickCallback?.onItemClicked(movie?.id)
            }
        }
    }

    // buat interface yang akkan membuat itemnya mencjadi clickable
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(id: Int?)
    }

    // fungsi callback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // NETWORK STATE CLASS
    class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(networkState: NetworkState?) {
            // cek ketika state loading maka tampilkan loading
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.movie_network_progressbar.visibility = View.VISIBLE
            } else {
                itemView.movie_network_progressbar.visibility = View.GONE
            }

            // jika error
            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.movie_network_messages.visibility = View.VISIBLE
                itemView.movie_network_messages.text = networkState.message
            }
            //jika halaman habis
            else if (networkState != null && networkState == NetworkState.EMPTY) {
                itemView.movie_network_messages.visibility = View.VISIBLE
                itemView.movie_network_messages.text = networkState.message
            } else {
                itemView.movie_network_messages.visibility = View.GONE
            }
        }
    }

}