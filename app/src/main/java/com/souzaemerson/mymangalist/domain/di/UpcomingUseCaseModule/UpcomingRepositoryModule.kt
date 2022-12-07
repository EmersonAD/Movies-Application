package com.souzaemerson.mymangalist.domain.di.UpcomingUseCaseModule

import com.souzaemerson.mymangalist.data.repository.movie.upcoming.UpcomingMoviesRepositoryImpl
import com.souzaemerson.mymangalist.domain.repository.upcoming.UpcomingMoviesRepository
import org.koin.dsl.module

val upcomingMoviesRepository = module {
    single<UpcomingMoviesRepository> {
        UpcomingMoviesRepositoryImpl(apiService = get())
    }
}