package com.neobis.israil.newskotlinapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neobis.israil.newskotlinapp.R
import com.neobis.israil.newskotlinapp.adapters.viewHolder.ListNewsViewHolder
import com.neobis.israil.newskotlinapp.common.ISO8601Parser
import com.neobis.israil.newskotlinapp.model.WebSite
import com.neobis.israil.newskotlinapp.model.list_model.Article
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.util.*

class ListNewsAdapter(val articleList:MutableList<Article>,private var context: Context): RecyclerView.Adapter<ListNewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.news_layout,parent,false)
        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return articleList.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
    Picasso.with(context)
            .load(articleList[position].urlToImage)
            .into(holder.arcticle_image)

        if (articleList[position].title!!.length>65){
            holder.arcticle_title.text= articleList[position].title!!.substring(0,65)+"..."
        }else{
            holder.arcticle_title.text= articleList[position].title!!
        }

        if (articleList[position].publishedAt!=null){
            var date: Date?=null
            try{
                date =ISO8601Parser.parse(articleList[position].publishedAt!!)

            }catch (ex:ParseException){
                ex.printStackTrace()
            }
            holder.arcticle_time.setReferenceTime(date!!.time)


        }
    }



}