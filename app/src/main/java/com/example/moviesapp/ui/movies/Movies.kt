package com.example.moviesapp.ui.movies

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

    @Parcelize
    data class Movie (
var movieName: String?,
var movieDate: String?,
var movieScore: String?,
var movieGenre: String?,
var movieDec: String?,
var moviePhoto: Int
): Parcelable


