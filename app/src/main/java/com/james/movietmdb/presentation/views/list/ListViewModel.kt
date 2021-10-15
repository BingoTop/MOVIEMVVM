package com.james.movietmdb.presentation.views.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.james.movietmdb.data.utils.BaseResponse
import com.james.movietmdb.domain.model.list.Movie
import com.james.movietmdb.data.model.list.toMovie
import com.james.movietmdb.domain.usecase.get_movies_coming_soon.GetMoviesComingSoonUseCase
import com.james.movietmdb.domain.usecase.get_movies_now_playing.GetMoviesNowPlayingUseCase
import com.james.movietmdb.domain.usecase.get_movies_popular.GetMoviesPopularUseCase
import com.james.movietmdb.domain.usecase.get_movies_top_rated.GetMoviesTopRatedUseCase
import com.james.movietmdb.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getMoviesComingSoonUseCase: GetMoviesComingSoonUseCase,
    private val getMoviesPopularUseCase: GetMoviesPopularUseCase,
    private val getMoviesTopRatedUseCase: GetMoviesTopRatedUseCase,
    private val getMoviesNowPlayingUseCase: GetMoviesNowPlayingUseCase
) : BaseViewModel() {
    private val _movieComingSoonList = MutableLiveData<BaseResponse<List<Movie>>>()
    val movieComingSoonList: LiveData<BaseResponse<List<Movie>>> get() = _movieComingSoonList
    private val _moviePopularList = MutableLiveData<BaseResponse<List<Movie>>>()
    val moviePopularList get() = _moviePopularList
    private val _movieTopRatedList = MutableLiveData<BaseResponse<List<Movie>>>()
    val movieTopRatedList get() = _movieTopRatedList
    private val _movieNowPlayingList = MutableLiveData<PagingData<Movie>>()
    val movieNowPlayingList:LiveData<PagingData<Movie>> get() = _movieNowPlayingList
    private val _response = MutableLiveData<BaseResponse<Boolean>>()
    val response:LiveData<BaseResponse<Boolean>> get() = _response

    init {
        compositeDisposable.add(
            getMoviesNowPlayingUseCase.execute()
                .cachedIn(viewModelScope)
                .subscribe({
                    _movieNowPlayingList.postValue(it)
                },{
                    Log.e("TAG", ": ${it.message}", )
                })
        )

        compositeDisposable.add(
            Single.zip(getMoviesComingSoonUseCase.execute().subscribeOn(Schedulers.io()),
                getMoviesPopularUseCase.execute().subscribeOn(Schedulers.io()),
                getMoviesTopRatedUseCase.execute().subscribeOn(Schedulers.io()),
                {upcoming,popular,topRadted ->
                    _movieComingSoonList.postValue(BaseResponse.Success(upcoming.results.map {
                        it.toMovie()
                    }.take(3)))
                    _moviePopularList.postValue(BaseResponse.Success(popular.results.map {
                        it.toMovie()
                    }.take(3)))
                    _movieTopRatedList.postValue(BaseResponse.Success(topRadted.results.map {
                        it.toMovie()
                    }.take(3)))
                }
            )
                .doOnSubscribe {
                    _response.postValue(BaseResponse.Loading())
                }
                .delay(500L,TimeUnit.MILLISECONDS)
                .doOnTerminate {
                    _response.postValue(BaseResponse.Loaded())
                }
                .subscribe()
        )
    }

    fun getComingSoonMovies() {
        compositeDisposable.add(
            getMoviesComingSoonUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _movieComingSoonList.postValue(BaseResponse.Success(it.results.map {
                        it.toMovie()
                    }.take(3)))
                }, {
                    _movieComingSoonList.postValue(BaseResponse.Error(it))
                    Log.d("TAG", "getMovieComingSoon: ${it} ")
                })
        )
    }

    fun getPopularMovies() {
        compositeDisposable.add(
            getMoviesPopularUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _moviePopularList.postValue(BaseResponse.Success(it.results.map {
                        it.toMovie()
                    }.take(3)))
                }, {
                    _moviePopularList.postValue(BaseResponse.Error(it))
                })
        )
    }

    fun getTopRatedMovies(){
        compositeDisposable.add(
            getMoviesTopRatedUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                           _movieTopRatedList.postValue(BaseResponse.Success(it.results.map {
                               it.toMovie()
                           }.take(3)))
                },{
                    _movieTopRatedList.postValue(BaseResponse.Error(it))
                })
        )
    }
}