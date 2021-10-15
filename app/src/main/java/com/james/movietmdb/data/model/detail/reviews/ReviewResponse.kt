package com.james.movietmdb.data.model.detail.reviews


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val reviewDtos: List<ReviewDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)