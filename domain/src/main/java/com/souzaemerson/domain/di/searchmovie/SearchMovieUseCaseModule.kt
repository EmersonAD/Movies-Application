package com.souzaemerson.domain.di.searchmovie

import com.souzaemerson.domain.usecase.search.SearchForMoviesUseCase
import com.souzaemerson.domain.usecase.search.SearchForMoviesUseCaseImpl
import org.koin.dsl.module

val searchForMoviesUseCaseModule = module {
    single<SearchForMoviesUseCase> {
        SearchForMoviesUseCaseImpl(get())
    }
}