package com.souzaemerson.mymangalist.domain.usecase.search

import com.souzaemerson.mymangalist.domain.mapper.ResultDomain

interface SearchForMoviesUseCase {
    suspend operator fun invoke(movieName: String, apikey: String): List<ResultDomain>
}