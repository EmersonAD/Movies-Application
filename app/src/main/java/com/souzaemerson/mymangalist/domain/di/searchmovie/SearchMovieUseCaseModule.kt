package com.souzaemerson.mymangalist.domain.di.searchmovie

import com.souzaemerson.mymangalist.domain.usecase.search.SearchForMoviesUseCase
import com.souzaemerson.mymangalist.domain.usecase.search.SearchForMoviesUseCaseImpl
import org.koin.dsl.module

val searchForMoviesUseCaseModule = module {
    single<SearchForMoviesUseCase> {
        SearchForMoviesUseCaseImpl(get(), get())
    }
}