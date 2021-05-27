package com.aziz.newappsbyphillip.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aziz.newappsbyphillip.R
import com.aziz.newappsbyphillip.databinding.ActivityNewsBinding
import com.aziz.newappsbyphillip.db.ArticleDatabase
import com.aziz.newappsbyphillip.repositories.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelFactory = NewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)

        binding.bottomNavigationView.setupWithNavController(nav_host_fragment_container.findNavController())
    }
}