package com.aziz.newappsbyphillip.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aziz.newappsbyphillip.models.Article

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticle(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}