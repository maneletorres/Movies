package com.maneletorres.simpletemplate.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.maneletorres.simpletemplate.databinding.ViewMovieCarouselItemBinding
import com.maneletorres.simpletemplate.domain.common.GlideApp
import com.maneletorres.simpletemplate.domain.model.IMDBMovieModel
import com.maneletorres.simpletemplate.domain.model.MovieModel
import com.maneletorres.simpletemplate.ui.home.MoviesHomeFragmentDirections

class MovieAdapter(
    private val items: List<IMDBMovieModel>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding: ViewMovieCarouselItemBinding = ViewMovieCarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class MovieViewHolder(
        private val binding: ViewMovieCarouselItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IMDBMovieModel) {
            with (binding) {
                GlideApp.with(root).load(item.image).into(movieCoverImageView)
                root.setOnClickListener { view ->
                    navigateToMovie(item.id, view)
                }
            }
        }

        private fun navigateToMovie(movieId: String, view: View) {
            val direction = MoviesHomeFragmentDirections.actionMoviesHomeFragmentToMovieDetailFragment(movieId)
            view.findNavController().navigate(direction)
        }
    }
}