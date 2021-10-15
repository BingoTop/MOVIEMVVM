package com.james.movietmdb.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.james.movietmdb.R
import com.james.movietmdb.data.utils.Constants

object BindingAdapter {
    @BindingAdapter("posterPath")
    @JvmStatic
    fun loadImage(imageView: ImageView,url:String?){
        url?.let {
            Glide.with(imageView.context)
                .load("${Constants.IMAGE_BASE_URL}${Constants.IMAGE_SIZE_MEDIUM}$it")
                .error(R.color.detail_tv_overview_text_color)
                .fitCenter()
                .transforms(CenterCrop(),RoundedCorners(imageView.context.resources.getDimension(R.dimen.item_movie_radius).toInt()))
                .into(imageView)
        }
    }
    @BindingAdapter("profilePath")
    @JvmStatic
    fun loadProfileImage(imageView: ImageView,url:String?){
        url?.let {
            Glide.with(imageView.context)
                .load("${Constants.IMAGE_BASE_URL}${Constants.IMAGE_SIZE_MEDIUM}$it")
                .error(R.color.detail_tv_overview_text_color)
                .fitCenter()
                .transforms(CenterCrop(),RoundedCorners(imageView.context.resources.getDimension(R.dimen.item_credit_profile_radius).toInt()))
                .into(imageView)
        }
    }
}