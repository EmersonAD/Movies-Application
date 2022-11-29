package com.souzaemerson.mymangalist.domain.usecase

import com.souzaemerson.mymangalist.const.API_KEY
import com.souzaemerson.mymangalist.const.LANGUAGE
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepository
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomain

class GetMoviesContentUseCaseImpl(private val repository: MovieRepository): GetMoviesContentUseCase {
    override suspend fun invoke(page: Int): List<ResultDomain> {
        val resultList = repository.getPopularMovies(API_KEY, LANGUAGE, page).results
        return TransformResultIntoDomain(resultList)
    }
}