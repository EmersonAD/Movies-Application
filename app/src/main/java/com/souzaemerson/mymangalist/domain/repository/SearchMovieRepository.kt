package com.souzaemerson.mymangalist.domain.repository

import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import retrofit2.Response

interface SearchMovieRepository {
    suspend fun searchMovie(apikey: String, language: String = "pt-BR", page: Int = 1, query: String) : Response<MovieResponse>
}