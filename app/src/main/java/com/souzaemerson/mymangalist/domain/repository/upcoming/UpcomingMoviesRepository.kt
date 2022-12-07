package com.souzaemerson.mymangalist.domain.repository.upcoming

import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import retrofit2.Response

interface UpcomingMoviesRepository {
    suspend fun getUpcomingMovies(apikey: String, language: String = "pt-BR", page: Int = 1): Response<MovieResponse>
}