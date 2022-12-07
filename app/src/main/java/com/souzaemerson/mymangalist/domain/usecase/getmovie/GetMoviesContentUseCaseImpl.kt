package com.souzaemerson.mymangalist.domain.usecase.getmovie

import android.util.Log
import com.souzaemerson.mymangalist.domain.repository.popular.MovieRepository
import com.souzaemerson.domain.usecase.getmovie.GetMoviesContentUseCase
import com.souzaemerson.const.LANGUAGE
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomain

class GetMoviesContentUseCaseImpl(private val repository: MovieRepository) :
    GetMoviesContentUseCase {
    override suspend fun invoke(page: Int, apikey: String): List<ResultDomain> {

        val movieResponse = repository.getPopularMovies(apikey, LANGUAGE, page)

        return if (movieResponse.code() == 200) {
            movieResponse.body()?.let {
                TransformResultIntoDomain(it.results)
            } ?: throw Exception("Response body cannot be null")
        } else {
            Log.e("Response getMovie: ", "${movieResponse.code()} - ${movieResponse.errorBody()}")
            throw IllegalArgumentException()
        }
    }
}