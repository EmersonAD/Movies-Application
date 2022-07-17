package com.souzaemerson.mymangalist.data.network

import com.souzaemerson.mymangalist.data.model.anime.AnimeResponse
import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("top/anime")
    suspend fun getTopAnimeList(): AnimeResponse
}