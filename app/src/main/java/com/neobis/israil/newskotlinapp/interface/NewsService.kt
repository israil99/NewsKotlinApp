package com.neobis.israil.newskotlinapp.`interface`

import com.neobis.israil.newskotlinapp.model.WebSite
import com.neobis.israil.newskotlinapp.model.list_model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {
    @get:GET("v2/sources?apiKey=dec054ddd8d242de8ec44de538151747")
    val sources: Call<WebSite>

    @GET
fun getNewsFromService(@Url url: String):Call<News>
}