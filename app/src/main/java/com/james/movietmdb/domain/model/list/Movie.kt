package com.james.movietmdb.domain.model.list

data class Movie(
    val posterPath:String?,
    val title:String,
    val releaseDate:String,
    val voteAverage:Double,
    val id:Int,
)
