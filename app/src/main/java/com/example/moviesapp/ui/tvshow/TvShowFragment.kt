package com.example.moviesapp.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.ui.tvshow.adapter.TVshowAdapter
import com.example.moviesapp.ui.tvshow.detailFragment.detailTvshow
import com.example.moviesapp.ui.tvshow.detailFragment.detailTvshow.Companion.DATA_DETAIL_TVSHOW
import kotlinx.android.synthetic.main.fragment_tvshow.*

class TvShowFragment : Fragment() {

    private val tvshow = ArrayList<TVshow>()

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
        // tambahkan semua item
        tvshow.addAll(getListTVshow())
        //panggil fungsi grid
        showRecyclerGrid()
    }

    // fungsi mengambil daftar movies dari array
    private fun getListTVshow(): ArrayList<TVshow>{
        val dataName = resources.getStringArray(R.array.data_name_tvshow)
        val dataDate = resources.getStringArray(R.array.data_date_tvshow)
        val dataScore = resources.getStringArray(R.array.data_score_tvshow)
        val dataGenre = resources.getStringArray(R.array.data_genre_tvshow)
        val dataDec = resources.getStringArray(R.array.data_dec_tvshow)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo_tvshow)

        val listTVshow = ArrayList<TVshow>()
        for(position in dataName.indices){
            val tvshow = TVshow(
                dataName[position],
                dataDate[position],
                dataScore[position],
                dataGenre[position],
                dataDec[position],
                dataPhoto.getResourceId(position, -1)
            )
            listTVshow.add(tvshow)
        }

        dataPhoto.recycle()
        return listTVshow
    }

    private fun showRecyclerGrid() {
        rv_tvshow.layoutManager = GridLayoutManager(context, 3)
        val gridHeroAdapter = TVshowAdapter(tvshow)
        rv_tvshow.adapter = gridHeroAdapter

        // buat fungsi untuk click callback
        gridHeroAdapter.setOnItemClickCallback( object : TVshowAdapter.OnItemClickCallback{
            override fun onItemClicked(data: TVshow) {

                //siapkan data parcelable
                    val fragGet = detailTvshow()
                    val bundle = Bundle()

                // masukkan ke bundle
                    bundle.putParcelable(DATA_DETAIL_TVSHOW, data)
                    fragGet.arguments = bundle

                // buat perpindahan fragment
                val mFragment = fragmentManager
                mFragment?.beginTransaction()?.apply {
                    add(R.id.nav_host_fragment, fragGet, detailTvshow::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }


            }
        })

    }

}
