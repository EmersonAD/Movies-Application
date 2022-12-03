package com.souzaemerson.data.repository.movie.popular

import com.souzaemerson.data.model.movie.MovieResponse
import retrofit2.Response

interface MovieRepository {
    suspend fun getPopularMovies(apikey: String, language: String, page: Int = 1): Response<MovieResponse>
}