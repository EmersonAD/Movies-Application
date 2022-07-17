package com.souzaemerson.mymangalist.data.repository.anime

import com.souzaemerson.mymangalist.data.model.anime.AnimeResponse

interface AnimeRepository {
    suspend fun getAnimeList(): AnimeResponse
}