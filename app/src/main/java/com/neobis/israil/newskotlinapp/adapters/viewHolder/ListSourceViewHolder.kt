package com.neobis.israil.newskotlinapp.adapters.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.neobis.israil.newskotlinapp.`interface`.ItemClickListener
import kotlinx.android.synthetic.main.source_news_layout.view.*


class ListSourceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener

    var soorce_title = itemView.source_news_name


    init {
        itemView.setOnClickListener(this)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener=itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!,adapterPosition)
    }
}