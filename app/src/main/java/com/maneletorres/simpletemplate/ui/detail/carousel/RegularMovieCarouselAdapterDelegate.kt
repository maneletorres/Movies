package com.maneletorres.simpletemplate.ui.detail.carousel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.maneletorres.simpletemplate.databinding.ViewMovieCarouselItemBinding
import com.maneletorres.simpletemplate.ui.home.MoviesHomeFragmentDirections

class RegularMovieCarouselAdapterDelegate() : AdapterDelegate<List<CarouselMovieItem>>() {

    override fun isForViewType(items: List<CarouselMovieItem>, position: Int): Boolean =
        items[position] is RegularCarouselMovieItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemBinding: ViewMovieCarouselItemBinding = ViewMovieCarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegularMovieCarouselItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        items: List<CarouselMovieItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as RegularMovieCarouselItemViewHolder).bind(items[position] as RegularCarouselMovieItem)
    }

    class RegularMovieCarouselItemViewHolder(
        private val binding: ViewMovieCarouselItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RegularCarouselMovieItem) {
            with (binding) {
                Glide.with(root).load(item.posterPath).into(movieCoverImageView)
            }
        }
    }
}