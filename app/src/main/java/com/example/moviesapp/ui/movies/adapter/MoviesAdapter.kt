package com.example.moviesapp.ui.movies.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.ui.movies.Movie
import kotlinx.android.synthetic.main.item_row_movie.view.*

class MoviesAdapter(private val listMovie : ArrayList<Movie>) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {


    inner class MoviesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie){
            with(itemView){
                Glide.with(itemView.context)
                    .load(movie.moviePhoto)
                    .into(movie_img)

                movie_name.text = movie.movieName
                movie_score.text = movie.movieScore + " Scores"
                movie_date.text = movie.movieDate

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(movie)}
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(listMovie[position])

        // ketika view di klik
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(listMovie[position])
        }
    }


    // buat interface yang akkan membuat itemnya mencjadi clickable
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }


}


