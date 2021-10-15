package com.james.movietmdb.presentation.views.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.james.movietmdb.R
import com.james.movietmdb.data.utils.Constants
import com.james.movietmdb.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class DetailActivity:AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var castAdapter: CreditAdapter
    private lateinit var reviewAdapter:ReviewAdapter
    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.vm = detailViewModel
        initViewModel()
        initAdapter()
        val movieId = intent.getIntExtra(Constants.MOVIE_ID,-1)
        if(movieId < 0){
            Toast.makeText(this,resources.getString(R.string.error_info),Toast.LENGTH_SHORT).show()
        }else{
            detailViewModel.getDetailMovie(movieId)
            detailViewModel.getCredits(movieId)
            detailViewModel.getReviews(movieId)
        }
        binding.detailIvBack.setOnClickListener(clickListener)
    }

    private val clickListener = object:View.OnClickListener{
        override fun onClick(v: View?) {
            when(v?.id){
                binding.detailIvBack.id ->{
                    finish()
                }
            }
        }

    }

    private fun initViewModel(){
        detailViewModel.detailMovie.observe(this,{
            binding.detailTvMovieTitle.text = it.title
            binding.detailTvOverview.text = it.description
            binding.detailTvReleaseDate.text = String.format(getString(R.string.detail_tv_release_date),it.releasedDate)
            binding.detailTvMovieAverage.text = it.average.toString()
            Glide.with(this)
                .load("${Constants.IMAGE_BASE_URL}${Constants.IMAGE_SIZE_MEDIUM}${it.posterPath}")
                .fitCenter()
                .transform(CenterCrop(),RoundedCorners(resources.getDimension(R.dimen.item_movie_radius).toInt()))
                .into(binding.detailIvPosterPath)

            Glide.with(this)
                .load("${Constants.IMAGE_BASE_URL}${Constants.IMAGE_SIZE_MAX}${it.posterPath}")
                .fitCenter()
                .into(binding.detailIvPosterBackground)

            if(!it.isAdult) binding.detailTvAdultTag.visibility = View.GONE
            binding.detailTvGenres.text = it.genre
            binding.detailRbStar.rating = round(it.average / 2).toFloat()
        })
        detailViewModel.casts.observe(this,{
            castAdapter.setCastList(it)
        })
        detailViewModel.reviews.observe(this,{
            if(it.size == 0){
                binding.detailTvReviewInfo.visibility = View.GONE
                binding.detailRvReview.visibility = View.GONE
            }else{
                reviewAdapter.setNewReviews(it)
            }
        })
    }

    private fun initAdapter(){
        initCastAdapter()
        initReviewAdapter()
    }

    private fun initCastAdapter(){
        castAdapter = CreditAdapter()
        binding.detailRvCredits.apply {
            adapter = castAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun initReviewAdapter(){
        reviewAdapter = ReviewAdapter()
        binding.detailRvReview.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity)
        }
    }


}