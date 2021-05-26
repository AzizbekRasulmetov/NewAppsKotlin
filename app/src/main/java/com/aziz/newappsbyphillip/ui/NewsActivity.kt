package com.aziz.newappsbyphillip.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aziz.newappsbyphillip.R
import com.aziz.newappsbyphillip.databinding.ActivityNewsBinding
import kotlinx.android.synthetic.main.activity_news.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)

        binding.bottomNavigationView.setupWithNavController(nav_host_fragment_container.findNavController())
    }
}