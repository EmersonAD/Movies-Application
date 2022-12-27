package com.souzaemerson.mymangalist.domain.usecase.getmovie

import android.util.Log
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomain
import com.souzaemerson.mymangalist.domain.repository.popular.MovieRepository

class GetMoviesContentUseCaseImpl(
    private val repository: MovieRepository,
    private val mapper: TransformResultIntoDomain
) : GetMoviesContentUseCase {
    override suspend fun domains(page: Int, apikey: String, language: String): List<ResultDomain> {

        repository.getPopularMovies(page = page, apikey = apikey, language = language).let { response ->

            return if (response.code() == 200) {
                response.body()?.let {
                    mapper.transform(it.results)
                } ?: throw Exception("Response body cannot be null")
            } else {
                Log.e(
                    "Response getMovie: ",
                    "${response.code()} - ${response.errorBody()}"
                )
                throw IllegalArgumentException()
            }
        }
    }
}