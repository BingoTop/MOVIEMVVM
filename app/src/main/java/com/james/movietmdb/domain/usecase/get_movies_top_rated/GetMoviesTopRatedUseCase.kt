package com.james.movietmdb.domain.usecase.get_movies_top_rated

import com.james.movietmdb.data.model.list.MovieResponse
import com.james.movietmdb.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMoviesTopRatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun execute(): Single<MovieResponse>{
        return movieRepository.getMoviesTopRated()
    }
}