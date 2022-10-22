package com.maneletorres.simpletemplate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.fragment.app.viewModels
import androidx.navigation.compose.rememberNavController
import com.maneletorres.simpletemplate.databinding.FragmentMoviesHomeBinding
import com.maneletorres.simpletemplate.ui.navigation.NavigationHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesHomeFragment : Fragment() {

    private val viewModel: MoviesHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMoviesHomeBinding.inflate(inflater, container, false).apply {
            composeView.setContent {
                val navController = rememberNavController()
                MaterialTheme {
                    // NavigationHost(navController)
                    MoviesHomeScreen(moviesHomeViewModel = viewModel)
                }
            }
        }
        return binding.root
    }
}