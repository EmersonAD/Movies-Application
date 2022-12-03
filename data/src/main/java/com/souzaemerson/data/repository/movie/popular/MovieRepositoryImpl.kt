package com.souzaemerson.data.repository.movie.popular

import com.souzaemerson.data.model.movie.MovieResponse
import retrofit2.Response

class MovieRepositoryImpl(private val api: com.souzaemerson.network.service.Service) :
    MovieRepository {
    override suspend fun getPopularMovies(apikey: String, language: String, page: Int): Response<MovieResponse> {
        return api.getPopularMovies(apikey, language, page)
    }
}