package com.aziz.newappsbyphillip.api

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)