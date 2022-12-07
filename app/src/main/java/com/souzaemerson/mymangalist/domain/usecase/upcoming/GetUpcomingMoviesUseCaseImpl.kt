package com.souzaemerson.mymangalist.domain.usecase.upcoming

import com.souzaemerson.const.API_KEY
import com.souzaemerson.mymangalist.data.model.movie.Result
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoUpcomingDomain
import com.souzaemerson.mymangalist.domain.mapper.UpcomingDomain
import com.souzaemerson.mymangalist.domain.repository.upcoming.UpcomingMoviesRepository

class GetUpcomingMoviesUseCaseImpl(private val repository: UpcomingMoviesRepository) :
    GetUpcomingMoviesUseCase {
    override suspend fun invoke(apikey: String, language: String, page: Int): List<UpcomingDomain> {

        val resultList = repository.getUpcomingMovies(API_KEY, page = page).body()?.results

        return getContentByResponseCode(resultList, page)
    }

    private suspend fun getContentByResponseCode(resultList: List<Result>?, page: Int) =
        when (repository.getUpcomingMovies(API_KEY, page = page).code()) {
            200 -> TransformResultIntoUpcomingDomain(resultList ?: throw Exception("Response cannot be null"))
            in 400..500 -> throw Exception("HttpError")
            else -> throw Exception("No content")
        }
}