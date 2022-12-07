package com.souzaemerson.mymangalist.data.repository.movie.upcoming

import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.data.network.service.Service
import com.souzaemerson.mymangalist.domain.repository.upcoming.UpcomingMoviesRepository
import retrofit2.Response

class UpcomingMoviesRepositoryImpl(private val apiService: Service): UpcomingMoviesRepository {
    override suspend fun getUpcomingMovies(
        apikey: String,
        language: String,
        page: Int
    ): Response<MovieResponse> = apiService.getUpcomingMovies(apikey, language, page)
}