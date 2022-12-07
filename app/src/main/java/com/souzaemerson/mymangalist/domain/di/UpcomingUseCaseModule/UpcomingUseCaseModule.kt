package com.souzaemerson.mymangalist.domain.di.UpcomingUseCaseModule

import com.souzaemerson.mymangalist.domain.usecase.upcoming.GetUpcomingMoviesUseCase
import com.souzaemerson.mymangalist.domain.usecase.upcoming.GetUpcomingMoviesUseCaseImpl
import org.koin.dsl.module

val upcomingMoviesUseCase = module {
    single<GetUpcomingMoviesUseCase> {
        GetUpcomingMoviesUseCaseImpl(repository = get())
    }
}