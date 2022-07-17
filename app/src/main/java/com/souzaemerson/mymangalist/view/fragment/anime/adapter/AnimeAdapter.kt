package com.souzaemerson.mymangalist.view.fragment.anime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.souzaemerson.mymangalist.data.model.anime.Data
import com.souzaemerson.mymangalist.databinding.ItemMangaBinding

class AnimeAdapter(private val animeList: List<Data>) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemMangaBinding.inflate(view, parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val animes = animeList[position]
        holder.bindView(animes)
    }

    override fun getItemCount(): Int = animeList.count()

    class AnimeViewHolder(private val binding: ItemMangaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(anime: Data) {
            binding.run {
                itemTitle.text = anime.title
                itemDescription.text = anime.synopsis
                itemReleaseDate.text = "Rank: ${anime.rank}"
                itemImagem.load(anime.images.jpg.image_url)
            }
        }

    }
}