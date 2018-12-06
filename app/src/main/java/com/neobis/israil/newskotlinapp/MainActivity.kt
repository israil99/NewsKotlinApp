package com.neobis.israil.newskotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.Gson
import com.neobis.israil.newskotlinapp.`interface`.NewsService
import com.neobis.israil.newskotlinapp.adapters.ListSourceAdapter
import com.neobis.israil.newskotlinapp.common.Common
import com.neobis.israil.newskotlinapp.model.WebSite
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init paperdb
        Paper.init(this)

        //init service
        mService = Common.newsService

        //init View
        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)
        }
        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)
        loadWebSiteSource(false)
    }

    private fun loadWebSiteSource(isRefresh: Boolean) {
        if (!isRefresh){
            val cache = Paper.book().read<String>("cache")
            if (cache!=null&&!cache.isBlank()&&cache!="null"){
                //Read cache
                val webSite = Gson().fromJson<WebSite>(cache,WebSite::class.java)
                adapter = ListSourceAdapter(baseContext,webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter
            }
            else{
                dialog.show()
                mService.sources.enqueue(object:Callback<WebSite>{
                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = adapter

                        //save in cache
                        Paper.book().write("cache",Gson().toJson(response!!.body()))
                        dialog.dismiss()
                    }

                })
            }
        }
        else{
            swipe_to_refresh.isRefreshing = true

            mService.sources.enqueue(object:Callback<WebSite>{
                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = adapter

                    //save in cache
                    Paper.book().write("cache",Gson().toJson(response!!.body()))

                    swipe_to_refresh.isRefreshing = false
                }

            })
        }
    }



}
