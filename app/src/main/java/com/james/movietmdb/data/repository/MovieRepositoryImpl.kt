package com.james.movietmdb.data.repository

import com.james.movietmdb.data.model.list.MovieResponse
import com.james.movietmdb.data.api.ListApiInterface
import com.james.movietmdb.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val listApi: ListApiInterface
): MovieRepository {

    override fun getMovieComingSoon(): Single<MovieResponse> {
        return listApi.getMoviesComingSoon()
    }

    override fun getMoviesPopular(): Single<MovieResponse> {
        return listApi.getMoviesPopular()
    }

    override fun getMoviesTopRated(): Single<MovieResponse> {
        return listApi.getMoviesTopRated()
    }

    override fun getMoviesNowPlaying(page: Int): Single<MovieResponse> {
        return listApi.getMoviesNowPlaying(page)
    }


}