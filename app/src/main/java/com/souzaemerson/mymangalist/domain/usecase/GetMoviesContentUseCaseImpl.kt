package com.souzaemerson.mymangalist.domain.usecase

import android.util.Log
import com.souzaemerson.mymangalist.const.API_KEY
import com.souzaemerson.mymangalist.const.LANGUAGE
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepository
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomain

class GetMoviesContentUseCaseImpl(private val repository: MovieRepository) :
    GetMoviesContentUseCase {
    override suspend fun invoke(page: Int): List<ResultDomain> {

        val movieResponse = repository.getPopularMovies(API_KEY, LANGUAGE, page)

        return if (movieResponse.code() == 200) {
            movieResponse.body()?.let {
                return TransformResultIntoDomain(it.results)
            } ?: throw Exception("Response body cannot be null")
        } else {
            Log.e("Response in movie api: ", "${movieResponse.code()} - ${movieResponse.errorBody()}")
            emptyList()
        }
    }
}