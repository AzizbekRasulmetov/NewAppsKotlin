package com.aziz.newappsbyphillip.repositories

import com.aziz.newappsbyphillip.api.RetrofitInstance
import com.aziz.newappsbyphillip.db.ArticleDatabase
import com.aziz.newappsbyphillip.models.Article

class NewsRepository(val database: ArticleDatabase) {

    suspend fun getAllNews(countryCode: String, pageNumber: Int) = RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) = RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article){
        database.getArticleDAO().addArticle(article)
    }

    fun getSavedNews() = database.getArticleDAO().getAllArticles()

    suspend fun deleteArticle(article: Article){
        database.getArticleDAO().deleteArticle(article)
    }

}