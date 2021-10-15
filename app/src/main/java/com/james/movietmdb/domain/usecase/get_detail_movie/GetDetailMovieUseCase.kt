package com.james.movietmdb.domain.usecase.get_detail_movie

import com.james.movietmdb.data.model.detail.detail_movie.DetailMovieDto
import com.james.movietmdb.domain.repository.DetailMovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetDetailMovieUseCase @Inject constructor(
    private val detailMovieRepository: DetailMovieRepository
) {
    fun execute(movieId:Int): Single<DetailMovieDto>{
        return detailMovieRepository.getDetailMovie(movieId)
    }
}