package com.james.movietmdb.data.model.detail.reviews


import com.google.gson.annotations.SerializedName
import com.james.movietmdb.domain.model.detail.reviews.Review

data class ReviewDto(
    @SerializedName("author")
    val author: String,
    @SerializedName("author_details")
    val authorDetails: AuthorDetails,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String
)


fun ReviewDto.toReview(): Review {
    return Review(
        author = author,
        content = content
    )
}