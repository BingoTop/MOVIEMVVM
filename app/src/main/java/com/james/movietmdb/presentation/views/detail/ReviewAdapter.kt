package com.james.movietmdb.presentation.views.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.james.movietmdb.R
import com.james.movietmdb.databinding.ItemReviewBinding
import com.james.movietmdb.domain.model.detail.reviews.Review

class ReviewAdapter:RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    private var reviewList = listOf<Review>()
    class ReviewViewHolder(val binding:ItemReviewBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review,parent,false)
        val viewHolder = ReviewViewHolder(ItemReviewBinding.bind(view))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.binding.review = review
    }

    override fun getItemCount(): Int = reviewList.size

    fun setNewReviews(newReviewList:List<Review>){
        this.reviewList = newReviewList
        notifyDataSetChanged()
    }
}