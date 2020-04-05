package com.example.moviesapp.ui.tvshow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.ui.movies.adapter.MoviesAdapter
import com.example.moviesapp.ui.tvshow.TVshow
import kotlinx.android.synthetic.main.item_row_tvshow.view.*

class TVshowAdapter(private val listTVshow : ArrayList<TVshow>) : RecyclerView.Adapter<TVshowAdapter.TVshowViewHolder>() {
    inner class TVshowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(tVshow: TVshow) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(tVshow.tvshowPhoto)
                    .into(tvshow_img)
                tvshow_name.text = tVshow.tvshowName
                tvshow_score.text = tVshow.tvshowScore + " Scores"
                tvshow_date.text = tVshow.tvshowDate

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVshowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_tvshow, parent, false)
        return TVshowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTVshow.size
    }

    override fun onBindViewHolder(holder: TVshowViewHolder, position: Int) {
        holder.bind(listTVshow[position])

        // ketika view di klik
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(listTVshow[position])
        }

    }

    // buat interface yang akkan membuat itemnya mencjadi clickable
    private var onItemClickCallback : OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: TVshow)
    }

    // fungsi callback
    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


}