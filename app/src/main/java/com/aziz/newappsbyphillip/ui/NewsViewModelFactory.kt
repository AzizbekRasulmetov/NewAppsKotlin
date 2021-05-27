package com.aziz.newappsbyphillip.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aziz.newappsbyphillip.repositories.NewsRepository
import java.lang.IllegalArgumentException

class NewsViewModelFactory(val repository: NewsRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}