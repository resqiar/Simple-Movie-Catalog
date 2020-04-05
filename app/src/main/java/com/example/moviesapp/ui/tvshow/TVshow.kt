package com.example.moviesapp.ui.tvshow

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVshow (
    var tvshowName: String?,
    var tvshowDate: String?,
    var tvshowScore: String?,
    var tvshowGenre: String?,
    var tvshowDec: String?,
    var tvshowPhoto: Int
): Parcelable