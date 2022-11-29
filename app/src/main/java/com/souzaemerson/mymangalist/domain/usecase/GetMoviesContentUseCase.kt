package com.souzaemerson.mymangalist.domain.usecase

import com.souzaemerson.mymangalist.domain.mapper.ResultDomain

interface GetMoviesContentUseCase {
    suspend operator fun invoke(page: Int = 1): List<ResultDomain>
}