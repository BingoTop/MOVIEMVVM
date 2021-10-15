package com.james.movietmdb.presentation.views.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.james.movietmdb.R
import com.james.movietmdb.databinding.ItemCreditBinding
import com.james.movietmdb.domain.model.detail.credits.Cast

class CreditAdapter:RecyclerView.Adapter<CreditAdapter.CreditViewHolder>() {
    private var castList:List<Cast> = listOf()
    class CreditViewHolder(val binding:ItemCreditBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreditViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_credit,parent,false)
        val viewHolder = CreditViewHolder(ItemCreditBinding.bind(view))
        return viewHolder
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        val cast = castList[position]
        holder.binding.cast = cast
    }

    override fun getItemCount(): Int =  castList.size

    fun setCastList(newCastList:List<Cast>){
        this.castList = newCastList
        notifyDataSetChanged()
    }
}