package com.aziz.newappsbyphillip.repositories

import com.aziz.newappsbyphillip.api.RetrofitInstance
import com.aziz.newappsbyphillip.db.ArticleDatabase

class NewsRepository(val database: ArticleDatabase) {

    suspend fun getAllNews(countryCode: String, pageNumber: Int) = RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)



}