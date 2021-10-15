package com.james.movietmdb.presentation.views.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.james.movietmdb.R
import com.james.movietmdb.data.utils.Constants
import com.james.movietmdb.databinding.ItemNowPlayingBinding
import com.james.movietmdb.domain.model.list.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.math.round

class NowPlayingMovieAdapter :
    PagingDataAdapter<Movie, NowPlayingMovieAdapter.NowPlayingMovieViewHolder>(COMPARATOR) {
    private val _clickSubject = PublishSubject.create<Movie>()
    val clickSubject get() = _clickSubject

    class NowPlayingMovieViewHolder(val binding: ItemNowPlayingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun getClickObserver(movie: Movie): Observable<Movie> {
            return Observable.create { e ->
                binding.root.setOnClickListener {
                    e.onNext(movie)
                }

            }
        }
    }

    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.binding.movie = it
            holder.getClickObserver(it)
                .debounce(Constants.MOVIE_CLICK_DEBOUNCE_TIME,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_clickSubject)
            val list = listOf(holder.binding.itemMovieIvStar1,holder.binding.itemMovieIvStar2,holder.binding.itemMovieIvStar3,holder.binding.itemMovieIvStar4,holder.binding.itemMovieIvStar5)
            val average = round(movie.voteAverage / 2).toInt()
            for(i in 0..average-1){
                list[i].setImageResource(R.drawable.star_active)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing, parent, false)
        val viewHolder = NowPlayingMovieViewHolder(ItemNowPlayingBinding.bind(view))
        return viewHolder
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }
}