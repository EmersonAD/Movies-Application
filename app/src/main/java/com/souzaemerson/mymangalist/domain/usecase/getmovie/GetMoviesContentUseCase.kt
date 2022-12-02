package com.souzaemerson.mymangalist.domain.usecase.getmovie

import com.souzaemerson.mymangalist.domain.mapper.ResultDomain

interface GetMoviesContentUseCase {
    suspend operator fun invoke(page: Int = 1, apikey: String): List<ResultDomain>
}