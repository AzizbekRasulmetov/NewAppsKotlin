package com.aziz.newappsbyphillip.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aziz.newappsbyphillip.R
import com.aziz.newappsbyphillip.adapters.NewsAdapter
import com.aziz.newappsbyphillip.ui.NewsActivity
import com.aziz.newappsbyphillip.ui.NewsViewModel
import com.aziz.newappsbyphillip.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.bumptech.glide.load.engine.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.searchNewsLiveData.observe(viewLifecycleOwner, { response ->
            when(response){
                is com.aziz.newappsbyphillip.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is com.aziz.newappsbyphillip.util.Resource.Error -> {
                    hideProgressBar()
                    response.message?.let{ message ->
                        Log.e("Error", "Error occured $message")
                    }
                }
                is com.aziz.newappsbyphillip.util.Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        var job: Job? = null

        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchForNews(editable.toString())
                    }
                }
            }
        }
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}