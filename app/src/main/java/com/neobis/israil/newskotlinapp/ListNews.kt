package com.neobis.israil.newskotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.neobis.israil.newskotlinapp.`interface`.NewsService
import com.neobis.israil.newskotlinapp.adapters.ListNewsAdapter
import com.neobis.israil.newskotlinapp.adapters.ListSourceAdapter
import com.neobis.israil.newskotlinapp.common.Common
import com.neobis.israil.newskotlinapp.model.list_model.Article
import com.neobis.israil.newskotlinapp.model.list_model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {
    var webHotUrl: String? = null
    var source = ""
    lateinit var dialog: android.app.AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService

        dialog = SpotsDialog(this)

        swipe_to_refresh.setOnRefreshListener {
            loadNews(source, true)
        }
        diagonal_layout.setOnClickListener {
            //implement soon
        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if (intent != null) {
            source = intent.getStringExtra("source")
            if (!source.isEmpty()) {
                loadNews(source, false)
            }
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {
        if (isRefreshed) {
            dialog.show()
            mService.getNewsFromService(Common.getNewsApi(source!!))
                    .enqueue(object : Callback<News> {
                        override fun onFailure(call: Call<News>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>) {
                            dialog.dismiss()
                            Picasso.with(baseContext)
                                    .load(response.body()!!.articles!![0].urlToImage)
                                    .into(top_image)

                            top_title.text = response.body()!!.articles!![0].title
                            top_author.text = response.body()!!.articles!![0].author

                            webHotUrl = response.body()!!.articles!![0].url

                            var removeFirstItem = response!!.body()!!.articles
                            removeFirstItem.drop(1)

                            adapter = ListNewsAdapter(removeFirstItem as MutableList<Article>, baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter

                        }

                    })
        } else {
            swipe_to_refresh.isRefreshing = true
            mService.getNewsFromService(Common.getNewsApi(source!!))
                    .enqueue(object : Callback<News> {
                        override fun onFailure(call: Call<News>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>) {
                            swipe_to_refresh.isRefreshing = false
                            Picasso.with(baseContext)
                                    .load(response.body()!!.articles!![0].urlToImage)
                                    .into(top_image)

                            top_title.text = response.body()!!.articles!![0].title
                            top_author.text = response.body()!!.articles!![0].author

                            webHotUrl = response.body()!!.articles!![0].url

                            var removeFirstItem = response!!.body()!!.articles
                            removeFirstItem.drop(1)

                            adapter = ListNewsAdapter(removeFirstItem as MutableList<Article>, baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter
                        }

                    })
        }
    }
}
