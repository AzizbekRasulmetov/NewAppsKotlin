package com.aziz.newappsbyphillip.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aziz.newappsbyphillip.R
import com.aziz.newappsbyphillip.db.ArticleDatabase
import com.aziz.newappsbyphillip.repositories.NewsRepository
import com.aziz.newappsbyphillip.ui.NewsActivity
import com.aziz.newappsbyphillip.ui.NewsViewModel
import com.aziz.newappsbyphillip.ui.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, NewsViewModelFactory(NewsRepository(ArticleDatabase(requireContext())))).get(NewsViewModel::class.java)
        val article = args.article
        Log.d("Infoforme", article.toString())
        webView.apply {
            webViewClient = WebViewClient() //Used for displaying it directly in phone rather than opening in browser
            loadUrl(article.url)
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

}