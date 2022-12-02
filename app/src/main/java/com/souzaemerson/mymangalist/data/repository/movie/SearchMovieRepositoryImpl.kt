package com.souzaemerson.mymangalist.data.repository.movie

import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.data.network.Service
import com.souzaemerson.mymangalist.domain.repository.SearchMovieRepository
import retrofit2.Response

class SearchMovieRepositoryImpl(private val apiService: Service) : SearchMovieRepository {
    override suspend fun searchMovie(
        apikey: String,
        language: String,
        page: Int,
        query: String
    ): Response<MovieResponse> = apiService.searchForMovies(apikey, language, page, query)
}