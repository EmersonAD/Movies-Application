package com.souzaemerson.mymangalist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.souzaemerson.mymangalist.R
import com.souzaemerson.mymangalist.databinding.MovieItemBinding
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain

class MovieAdapter(
    private val moviesList: List<ResultDomain>,
    private val itemClick: ((item: ResultDomain) -> Unit)
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(view, parent, false)
        return MyViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movies = moviesList[position]
        holder.bindView(movies)
    }

    override fun getItemCount(): Int = moviesList.size

    class MyViewHolder(
        private val binding: MovieItemBinding,
        private val itemClick: (item: ResultDomain) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(movie: ResultDomain) {
            binding.run {
                val initPath = "https://image.tmdb.org/t/p/w500"
                val popularityRate = "Pop: ${movie.popularity.toInt()}"
                itemReleaseDate.text = popularityRate
                itemImage.load(initPath.plus(movie.poster_path)){
                    placeholder(R.drawable.placeloading)
                }
            }
            itemView.setOnClickListener {
                itemClick.invoke(movie)
            }
        }
    }
}