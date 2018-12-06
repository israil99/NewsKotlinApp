package com.neobis.israil.newskotlinapp.common

import com.neobis.israil.newskotlinapp.`interface`.NewsService
import com.neobis.israil.newskotlinapp.remote.RetrofitClient

object Common{
    val BASE_URL="https://newsapi.org/"
    val API_KEY ="dec054ddd8d242de8ec44de538151747"

    val newsService: NewsService
        get() = RetrofitClient.getCLient(BASE_URL).create(NewsService::class.java)
}