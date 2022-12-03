package com.souzaemerson.data.repository.movie.search

import com.souzaemerson.data.model.movie.MovieResponse
import com.souzaemerson.network.service.Service
import retrofit2.Response

class SearchMovieRepositoryImpl(private val apiService: com.souzaemerson.network.service.Service) :
    SearchMovieRepository {
    override suspend fun searchMovie(
        apikey: String,
        language: String,
        page: Int,
        query: String
    ): Response<MovieResponse> = apiService.searchForMovies(apikey, language, page, query)
}