package com.neobis.israil.newskotlinapp.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.neobis.israil.newskotlinapp.ListNews
import com.neobis.israil.newskotlinapp.R
import com.neobis.israil.newskotlinapp.`interface`.ItemClickListener
import com.neobis.israil.newskotlinapp.adapters.viewHolder.ListSourceViewHolder
import com.neobis.israil.newskotlinapp.model.WebSite

class ListSourceAdapter(private var context: Context, private val webSite: WebSite): RecyclerView.Adapter<ListSourceViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =inflater.inflate(R.layout.source_news_layout,parent,false)
        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return webSite.sources!!.size
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        holder.soorce_title.text = webSite.sources!![position].name
        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context,ListNews::class.java)
                intent.putExtra("source",webSite.sources!![position].id)
                context.startActivity(intent)
            }

        })
    }


}