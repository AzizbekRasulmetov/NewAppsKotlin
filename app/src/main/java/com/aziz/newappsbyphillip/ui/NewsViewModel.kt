package com.aziz.newappsbyphillip.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aziz.newappsbyphillip.models.Article
import com.aziz.newappsbyphillip.models.NewsResponse
import com.aziz.newappsbyphillip.repositories.NewsRepository
import com.aziz.newappsbyphillip.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val repository: NewsRepository): ViewModel() {

    val newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null
    val searchNewsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    init{
        getAllNews("us")
    }

    fun getAllNews(countryCode: String) = viewModelScope.launch {
        newsLiveData.postValue(Resource.Loading())
        val response = repository.getAllNews(countryCode, breakingNewsPage)
        newsLiveData.postValue(handleResponse(response))
    }

    fun searchForNews(searchQuery: String) = viewModelScope.launch {
        searchNewsLiveData.postValue(Resource.Loading())
        val response = repository.searchNews(searchQuery, searchNewsPage)
        searchNewsLiveData.postValue(handleSearchResponse(response))
    }

    private fun handleResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let {
                breakingNewsPage++
                if(breakingNewsResponse == null){
                    breakingNewsResponse = it
                }else{
                    Log.d("ArticlesLog", breakingNewsResponse?.articles?.size.toString())
                    breakingNewsResponse?.articles?.addAll(it.articles)
                    Log.d("ArticlesLog", breakingNewsResponse?.articles?.size.toString())
                }
                return Resource.Success(breakingNewsResponse?: it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let{
                searchNewsPage++
                if(searchNewsResponse == null){
                    searchNewsResponse = it
                }else{
                    searchNewsResponse?.articles?.addAll(it.articles)
                }
                return Resource.Success(searchNewsResponse?: it)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
            repository.upsert(article)
    }

    fun getSavedNews() = repository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

}