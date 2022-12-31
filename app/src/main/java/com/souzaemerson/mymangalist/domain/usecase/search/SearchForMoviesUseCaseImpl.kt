package com.souzaemerson.mymangalist.domain.usecase.search

import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomain
import com.souzaemerson.mymangalist.domain.repository.search.SearchMovieRepository

class SearchForMoviesUseCaseImpl(
    private val repositoryImpl: SearchMovieRepository,
    private val mapper: TransformResultIntoDomain
) : SearchForMoviesUseCase {
    override suspend fun invoke(movieName: String, apikey: String): List<ResultDomain> {

        val response = repositoryImpl.searchMovie(apikey = apikey, query = movieName, page = 1)

        return if (response.code() == 200) {
            response.body()?.let {
                mapper.transform(it.results)
            } ?: throw IllegalArgumentException("Response body cannot be null")
        } else {
            throw IllegalArgumentException("Search Movie Response: ${response.code()} - ${response.errorBody()}")
        }
    }
}