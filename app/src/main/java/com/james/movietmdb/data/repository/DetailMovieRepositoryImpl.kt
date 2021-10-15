package com.james.movietmdb.data.repository

import com.james.movietmdb.data.model.detail.credits.Credits
import com.james.movietmdb.data.model.detail.detail_movie.DetailMovieDto
import com.james.movietmdb.data.model.detail.reviews.ReviewResponse
import com.james.movietmdb.data.api.DetailApiInterface
import com.james.movietmdb.domain.repository.DetailMovieRepository
import io.reactivex.Single
import javax.inject.Inject

class DetailMovieRepositoryImpl @Inject constructor(
    private val detailApiInterface: DetailApiInterface
): DetailMovieRepository {

    override fun getDetailMovie(movieId: Int): Single<DetailMovieDto> {
        return detailApiInterface.getDetailMovie(movieId)
    }

    override fun getCredits(movieId: Int): Single<Credits> {
        return detailApiInterface.getCredits(movieId)
    }

    override fun getReviews(movieId: Int): Single<ReviewResponse> {
        return detailApiInterface.getReviews(movieId)
    }
}