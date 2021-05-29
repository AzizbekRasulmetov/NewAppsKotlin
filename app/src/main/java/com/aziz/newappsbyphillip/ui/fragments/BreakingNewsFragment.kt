package com.aziz.newappsbyphillip.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aziz.newappsbyphillip.R
import com.aziz.newappsbyphillip.adapters.NewsAdapter
import com.aziz.newappsbyphillip.db.ArticleDatabase
import com.aziz.newappsbyphillip.repositories.NewsRepository
import com.aziz.newappsbyphillip.ui.NewsActivity
import com.aziz.newappsbyphillip.ui.NewsViewModel
import com.aziz.newappsbyphillip.ui.NewsViewModelFactory
import com.aziz.newappsbyphillip.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var isScrolling = false
    private var isLastPage = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(NewsRepository(ArticleDatabase(requireContext())))
        ).get(NewsViewModel::class.java)
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        val totalPages = it.totalResults / 20 + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("Error", "Error occured $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val isNotAtTheBeginning = layoutManager.findFirstVisibleItemPosition() >= 0
            val isTotalMoreThanVisible = layoutManager.itemCount >= 20
            isLastPage =
                layoutManager.findFirstVisibleItemPosition() + layoutManager.childCount >= layoutManager.itemCount
            if (isScrolling && isLastPage && isNotAtTheBeginning) {
                //Fetch new items
                viewModel.getAllNews("us")
                isScrolling = false
            }
        }
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isScrolling = true
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isScrolling = false
    }


}

