package com.souzaemerson.mymangalist.data.repository.movie

import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import retrofit2.Response

interface MovieRepository {
    suspend fun getPopularMovies(apikey: String, language: String, page: Int = 1): Response<MovieResponse>
}