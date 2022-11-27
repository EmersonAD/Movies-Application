package com.souzaemerson.mymangalist.presentation.fragment.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.souzaemerson.mymangalist.data.model.movie.Result
import com.souzaemerson.mymangalist.databinding.ItemMangaBinding

class MovieAdapter(
    private val moviesList: List<Result>,
    private val itemClick: ((item: Result) -> Unit)
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemMangaBinding.inflate(view, parent, false)
        return MyViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movies = moviesList[position]
        holder.bindView(movies)
    }

    override fun getItemCount(): Int = moviesList.size

    class MyViewHolder(
        private val binding: ItemMangaBinding,
        private val itemClick: (item: Result) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(movie: Result) {
            binding.run {
                val initPath = "https://image.tmdb.org/t/p/w500"
                val popularityRate = "Pop: ${movie.popularity.toInt()}"
                itemTvTitle.text = movie.title
                itemReleaseDate.text = popularityRate
                itemImagem.load(initPath.plus(movie.poster_path))
            }
            itemView.setOnClickListener {
                itemClick.invoke(movie)
            }
        }
    }
}