package com.souzaemerson.mymangalist.view.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.souzaemerson.mymangalist.data.model.movie.Result
import com.souzaemerson.mymangalist.databinding.ItemMangaBinding
import java.text.DecimalFormat
import java.text.Format
import kotlin.random.Random

class MovieAdapter(
    private val moviesList: List<Result>,
    private val itemClick: ((item: Result) -> Unit)
) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

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
                itemTitle.text = movie.title
                itemDescription.text = movie.overview
                itemReleaseDate.text = "Rank: ${movie.popularity}"
                itemImagem.load("https://image.tmdb.org/t/p/original/${movie.poster_path}")
            }
            itemView.setOnClickListener {
                itemClick.invoke(movie)
            }
        }
    }
}