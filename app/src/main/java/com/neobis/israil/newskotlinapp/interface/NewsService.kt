package com.neobis.israil.newskotlinapp.`interface`

import com.neobis.israil.newskotlinapp.model.WebSite
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @get:GET("v2/sources?apiKey=dec054ddd8d242de8ec44de538151747")
    val sources: Call<WebSite>
}