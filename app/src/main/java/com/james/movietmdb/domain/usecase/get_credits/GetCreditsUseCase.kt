package com.james.movietmdb.domain.usecase.get_credits

import com.james.movietmdb.data.model.detail.credits.Credits
import com.james.movietmdb.domain.repository.DetailMovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCreditsUseCase @Inject constructor(
    private val detailMovieRepository: DetailMovieRepository
) {
    fun execute(movieId:Int): Single<Credits>{
        return detailMovieRepository.getCredits(movieId)
    }
}