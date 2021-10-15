package com.james.movietmdb.data.model.list


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPage:Int,
    @SerializedName("page")
    val page:Int
)