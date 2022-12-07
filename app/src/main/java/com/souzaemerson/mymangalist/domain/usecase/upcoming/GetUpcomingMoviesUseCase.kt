package com.souzaemerson.mymangalist.domain.usecase.upcoming

import com.souzaemerson.mymangalist.domain.mapper.UpcomingDomain

interface GetUpcomingMoviesUseCase {
    suspend operator fun invoke(apikey: String, language: String, page: Int): List<UpcomingDomain>
}