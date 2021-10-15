package com.james.movietmdb.presentation.views.list

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.james.movietmdb.domain.model.list.Movie
import com.james.movietmdb.data.model.list.toMovie
import com.james.movietmdb.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NowPlayingMoviePagingSource @Inject constructor(
    private val movieRepository: MovieRepository
) : RxPagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition
        if(anchorPosition == null){
            return null
        }
        val anchorPage = state.closestPageToPosition(anchorPosition)
        if(anchorPage == null){
            return null
        }
        val prevKey = anchorPage.prevKey
        if(prevKey != null){
            return prevKey + 1
        }
        val nextKey = anchorPage.nextKey
        if(nextKey != null){
            return nextKey -1
        }
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val nextPageNumber = params.key ?: 1

        return movieRepository.getMoviesNowPlaying(nextPageNumber)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results.map {
                        it.toMovie()
                    },
                    prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                    nextKey = if (nextPageNumber == it.totalPage) null else it.page + 1
                ) as LoadResult<Int, Movie>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }


}