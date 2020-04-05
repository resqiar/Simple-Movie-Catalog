package com.example.moviesapp.ui.tvshow.detailFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.moviesapp.R
import com.example.moviesapp.ui.tvshow.TVshow
import kotlinx.android.synthetic.main.fragment_detail_tvshow.*

/**
 * A simple [Fragment] subclass.
 */
class detailTvshow : Fragment() {

    companion object {
        const val DATA_DETAIL_TVSHOW = "data_detail_tvshow"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // terima data dari fragment
            val data = arguments?.getParcelable<TVshow>("data_detail_tvshow")


        if (data != null) {
            tv_name_tvshow.text = data.tvshowName
            tv_date_tvshow.text = data.tvshowDate
            tv_dec_tvshow.text = data.tvshowDec
            tv_genre_tvshow.text = data.tvshowGenre
            tv_score_tvshow.text = data.tvshowScore + " Scores"
            tv_img_tvshow.setImageResource(data.tvshowPhoto)
        }

        // btn watchlist
        btn_watchlist.setOnClickListener {
            Toast.makeText(context, data?.tvshowName + " " + getString(R.string.btn_add_watchlist), Toast.LENGTH_SHORT).show()
        }


    }


}
