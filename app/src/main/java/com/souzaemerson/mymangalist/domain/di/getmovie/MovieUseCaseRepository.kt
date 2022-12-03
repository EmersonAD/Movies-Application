package com.souzaemerson.mymangalist.domain.di.getmovie

import com.souzaemerson.domain.usecase.getmovie.GetMoviesContentUseCase
import com.souzaemerson.mymangalist.domain.usecase.getmovie.GetMoviesContentUseCaseImpl
import org.koin.dsl.module

val getMoviesUseCaseModule = module {
    single<GetMoviesContentUseCase> {
        GetMoviesContentUseCaseImpl(get())
    }
}