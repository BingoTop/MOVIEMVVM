package com.james.movietmdb.domain.repository

import com.james.movietmdb.data.model.list.MovieResponse
import io.reactivex.Single

interface MovieRepository {

    fun getMovieComingSoon(): Single<MovieResponse>
    fun getMoviesPopular():Single<MovieResponse>
    fun getMoviesTopRated():Single<MovieResponse>
    fun getMoviesNowPlaying(page:Int):Single<MovieResponse>
}