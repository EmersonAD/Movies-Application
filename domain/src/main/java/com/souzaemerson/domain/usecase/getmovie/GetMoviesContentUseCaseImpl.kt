package com.souzaemerson.domain.usecase.getmovie

import android.util.Log
import com.souzaemerson.mymangalist.const.LANGUAGE
import com.souzaemerson.domain.mapper.ResultDomain
import com.souzaemerson.domain.mapper.TransformResultIntoDomain
import com.souzaemerson.data.repository.movie.popular.MovieRepository

class GetMoviesContentUseCaseImpl(private val repository: MovieRepository) :
    GetMoviesContentUseCase {
    override suspend fun invoke(page: Int, apikey: String): List<ResultDomain> {

        val movieResponse = repository.getPopularMovies(apikey, LANGUAGE, page)

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