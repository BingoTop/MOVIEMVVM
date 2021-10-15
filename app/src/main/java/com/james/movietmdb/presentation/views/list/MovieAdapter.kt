package com.james.movietmdb.presentation.views.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.james.movietmdb.R
import com.james.movietmdb.data.utils.Constants
import com.james.movietmdb.databinding.ItemMovieBinding
import com.james.movietmdb.domain.model.list.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.math.round

class MovieAdapter:RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movieList = arrayListOf<Movie>()
    private val _movieClickSubject = PublishSubject.create<Movie>()
    val movieClickSubject get() = _movieClickSubject

    class MovieViewHolder(val binding:ItemMovieBinding):RecyclerView.ViewHolder(binding.root) {

        fun getItemClickObserver(movie: Movie):Observable<Movie>{
            return Observable.create {
                e->
                binding.root.setOnClickListener {
                    e.onNext(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        val viewHolder = MovieViewHolder(ItemMovieBinding.bind(view))
        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.movie = movie
        val list = arrayListOf(holder.binding.itemMovieIvStar1,holder.binding.itemMovieIvStar2,holder.binding.itemMovieIvStar3,holder.binding.itemMovieIvStar4,holder.binding.itemMovieIvStar5)
        val average = round(movie.voteAverage/2).toInt()
        for(i in 0..average-1){
            list[i].setImageResource(R.drawable.star_active)
        }

        holder.getItemClickObserver(movie)
            .debounce(Constants.MOVIE_CLICK_DEBOUNCE_TIME,TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(_movieClickSubject)
    }

    override fun getItemCount(): Int  = movieList.size

    fun setMovieList(newMovieList:List<Movie>){
        this.movieList = newMovieList as ArrayList<Movie>
        notifyDataSetChanged()
    }


}