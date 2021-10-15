package com.james.movietmdb.data.api

import com.james.movietmdb.data.model.detail.credits.Credits
import com.james.movietmdb.data.model.detail.detail_movie.DetailMovieDto
import com.james.movietmdb.data.model.detail.reviews.ReviewResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApiInterface {

    @GET("{movieId}")
    fun getDetailMovie(@Path("movieId")movieId:Int): Single<DetailMovieDto>

    @GET("{movieId}/credits")
    fun getCredits(@Path("movieId")movieId: Int):Single<Credits>

    @GET("{movieId}/reviews")
    fun getReviews(@Path("movieId")movieId: Int):Single<ReviewResponse>
}