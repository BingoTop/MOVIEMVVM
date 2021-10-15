package com.james.movietmdb.data.api

import com.james.movietmdb.data.model.list.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ListApiInterface {

    @GET("now_playing")
    fun getMoviesNowPlaying(@Query("page")page: Int):Single<MovieResponse>

    @GET("upcoming")
    fun getMoviesComingSoon(@Query("page")page:Int = 1):Single<MovieResponse>

    @GET("popular")
    fun getMoviesPopular(@Query("page")page:Int = 1):Single<MovieResponse>

    @GET("top_rated")
    fun getMoviesTopRated(@Query("page")page:Int = 1):Single<MovieResponse>

}