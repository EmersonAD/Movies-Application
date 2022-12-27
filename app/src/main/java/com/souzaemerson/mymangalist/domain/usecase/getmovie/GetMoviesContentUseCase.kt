package com.souzaemerson.mymangalist.domain.usecase.getmovie

import com.souzaemerson.mymangalist.domain.mapper.ResultDomain

interface GetMoviesContentUseCase {
    suspend fun domains(page: Int, apikey: String, language: String): List<ResultDomain>
}