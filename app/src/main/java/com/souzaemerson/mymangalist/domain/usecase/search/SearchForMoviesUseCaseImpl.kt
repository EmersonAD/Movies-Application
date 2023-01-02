package com.souzaemerson.mymangalist.domain.usecase.search

import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomain
import com.souzaemerson.mymangalist.domain.repository.search.SearchMovieRepository
import retrofit2.Response

class SearchForMoviesUseCaseImpl(
    private val repositoryImpl: SearchMovieRepository,
    private val mapper: TransformResultIntoDomain
) : SearchForMoviesUseCase {
    override suspend fun invoke(movieName: String, apikey: String): List<ResultDomain> {

        val response = repositoryImpl.searchMovie(apikey = apikey, query = movieName, page = 1)

        return setContentByResponseCode(response)
    }

    private fun setContentByResponseCode(response: Response<MovieResponse>): List<ResultDomain> {
        return when (response.code()) {
            200 -> mapper.transform(response.body()?.results ?: throw IllegalArgumentException("Response body cannot be null"))
            in 400..500 -> throw IllegalArgumentException("Error: ${response.code()}")
            else -> throw IllegalArgumentException("Search Movie Response: ${response.code()} - ${response.errorBody()}")
        }
    }
}