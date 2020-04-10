package com.example.moviesapp.backend.data.`object`


import com.google.gson.annotations.SerializedName

data class ListTV(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultTV>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)