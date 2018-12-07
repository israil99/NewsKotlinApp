package com.neobis.israil.newskotlinapp.adapters.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.neobis.israil.newskotlinapp.`interface`.ItemClickListener
import kotlinx.android.synthetic.main.news_layout.view.*

class ListNewsViewHolder (itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private lateinit var itemClickListener: ItemClickListener

    var arcticle_title = itemView.arcticle_title
    var arcticle_image = itemView.arcticle_image
    var arcticle_time= itemView.arcticle_time

    init {
        itemView.setOnClickListener(this)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener=itemClickListener
    }

    override fun onClick(v: View) {
        itemClickListener.onClick(v,adapterPosition)
    }
}