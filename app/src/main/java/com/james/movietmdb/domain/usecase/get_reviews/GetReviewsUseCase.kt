package com.james.movietmdb.domain.usecase.get_reviews

import com.james.movietmdb.data.model.detail.reviews.ReviewResponse
import com.james.movietmdb.domain.repository.DetailMovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val detailMovieRepository: DetailMovieRepository
) {
    fun execute(movieId:Int):Single<ReviewResponse>{
        return detailMovieRepository.getReviews(movieId)
    }
}