package com.souzaemerson.mymangalist.data.repository.movie

import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.data.network.Service
import retrofit2.Response

class MovieRepositoryImpl(private val api: Service) : MovieRepository {
    override suspend fun getPopularMovies(apikey: String, language: String, page: Int): Response<MovieResponse> {
        return api.getPopularMovies(apikey, language, page)
    }
}