package com.aziz.newappsbyphillip.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aziz.newappsbyphillip.R
import com.aziz.newappsbyphillip.ui.NewsActivity
import com.aziz.newappsbyphillip.ui.NewsViewModel

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }

}