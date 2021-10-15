package com.james.movietmdb.domain.repository

import com.james.movietmdb.data.model.detail.credits.Credits
import com.james.movietmdb.data.model.detail.detail_movie.DetailMovieDto
import com.james.movietmdb.data.model.detail.reviews.ReviewResponse
import io.reactivex.Single

interface DetailMovieRepository {

    fun getDetailMovie(movieId:Int): Single<DetailMovieDto>
    fun getCredits(movieId: Int):Single<Credits>
    fun getReviews(movieId: Int):Single<ReviewResponse>

}