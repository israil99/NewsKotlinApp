package com.neobis.israil.newskotlinapp.model.list_model


data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

