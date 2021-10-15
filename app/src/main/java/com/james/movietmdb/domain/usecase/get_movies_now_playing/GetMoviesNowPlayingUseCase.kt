package com.james.movietmdb.domain.usecase.get_movies_now_playing

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.james.movietmdb.domain.model.list.Movie
import com.james.movietmdb.presentation.views.list.NowPlayingMoviePagingSource
import io.reactivex.Flowable
import javax.inject.Inject

class GetMoviesNowPlayingUseCase @Inject constructor(
    private val nowPlayingMoviePagingSource: NowPlayingMoviePagingSource
) {
    fun execute():Flowable<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {nowPlayingMoviePagingSource}
        ).flowable
    }
}