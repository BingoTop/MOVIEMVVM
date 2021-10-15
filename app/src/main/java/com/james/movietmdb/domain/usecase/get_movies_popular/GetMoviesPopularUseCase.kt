package com.james.movietmdb.domain.usecase.get_movies_popular

import com.james.movietmdb.data.model.list.MovieResponse
import com.james.movietmdb.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMoviesPopularUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun execute(): Single<MovieResponse>{
        return movieRepository.getMoviesPopular()
    }
}