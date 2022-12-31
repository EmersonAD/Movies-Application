package com.souzaemerson.mymangalist.domain.repository.search

import com.souzaemerson.const.LANGUAGE
import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import retrofit2.Response

interface SearchMovieRepository {
    suspend fun searchMovie(apikey: String, language: String = LANGUAGE, page: Int, query: String) : Response<MovieResponse>
}