package com.james.movietmdb.presentation.views.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.james.movietmdb.domain.model.detail.credits.Cast
import com.james.movietmdb.data.model.detail.credits.toCast
import com.james.movietmdb.domain.model.detail.detail_movie.DetailMovie
import com.james.movietmdb.data.model.detail.detail_movie.toDetailMovie
import com.james.movietmdb.domain.model.detail.reviews.Review
import com.james.movietmdb.data.model.detail.reviews.toReview
import com.james.movietmdb.domain.usecase.get_credits.GetCreditsUseCase
import com.james.movietmdb.domain.usecase.get_detail_movie.GetDetailMovieUseCase
import com.james.movietmdb.domain.usecase.get_reviews.GetReviewsUseCase
import com.james.movietmdb.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase
) : BaseViewModel() {
    private val _detailMovie = MutableLiveData<DetailMovie>()
    val detailMovie: LiveData<DetailMovie> get() = _detailMovie
    private val _casts = MutableLiveData<List<Cast>>()
    val casts: LiveData<List<Cast>> get() = _casts
    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> get() = _reviews

    fun getDetailMovie(movieId: Int) {
        compositeDisposable.add(
            getDetailMovieUseCase.execute(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _detailMovie.postValue(it.toDetailMovie())
                }, {})
        )
    }

    fun getCredits(movieId: Int) {
        compositeDisposable.add(
            getCreditsUseCase.execute(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _casts.postValue(it.castDto.map {
                        it.toCast()
                    })
                }, {})
        )
    }

    fun getReviews(movieId: Int) {
        compositeDisposable.add(
            getReviewsUseCase.execute(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _reviews.postValue(it.reviewDtos.map {
                        it.toReview()
                    })
                }, {})
        )
    }
}