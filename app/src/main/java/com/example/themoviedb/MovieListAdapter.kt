package com.example.themoviedb

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.themoviedb.api.MovieItem
import com.example.themoviedb.databinding.ItemListBinding

class MovieViewHolder(
    private val binding: ItemListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieItem: MovieItem, onItemClicked: (Uri) -> Unit) {
        binding.ivItem.load(movieItem.url) {
            placeholder(R.drawable.ic_launcher_foreground) //change to another image
        }

        binding.root.setOnClickListener {
            onItemClicked(movieItem.moviePageUri)
        }
    }
}

class MovieListAdapter(
    private val movieItems: List<MovieItem>, private val onItemClicked: (Uri) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieItems[position]
        holder.bind(item, onItemClicked)
    }

    override fun getItemCount() = movieItems.size
}