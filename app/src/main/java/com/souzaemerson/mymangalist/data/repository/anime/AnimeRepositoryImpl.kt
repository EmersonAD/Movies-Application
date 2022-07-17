package com.souzaemerson.mymangalist.data.repository.anime

import com.souzaemerson.mymangalist.data.model.anime.AnimeResponse
import com.souzaemerson.mymangalist.data.network.Service

class AnimeRepositoryImpl(private val api: Service) :
    AnimeRepository {
    override suspend fun getAnimeList(): AnimeResponse {
        return api.getTopAnimeList()
    }
}