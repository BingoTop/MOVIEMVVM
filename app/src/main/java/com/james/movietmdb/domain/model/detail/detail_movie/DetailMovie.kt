package com.james.movietmdb.domain.model.detail.detail_movie

data class DetailMovie(
    val title:String,
    val genre:String,
    val isAdult:Boolean,
    val average:Double,
    val posterPath:String?,
    val description:String?,
    val releasedDate:String
)
