package com.souzaemerson.domain.usecase.getmovie

import com.souzaemerson.domain.mapper.ResultDomain

interface GetMoviesContentUseCase {
    suspend operator fun invoke(page: Int = 1, apikey: String): List<ResultDomain>
}