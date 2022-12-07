package com.souzaemerson.mymangalist.domain.di.getmovie

import com.souzaemerson.mymangalist.data.repository.movie.popular.MovieRepositoryImpl
import com.souzaemerson.mymangalist.domain.repository.popular.MovieRepository
import org.koin.dsl.module

val movieRepositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }
}