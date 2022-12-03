package com.souzaemerson.domain.di.getmovie

import com.souzaemerson.mymangalist.data.repository.movie.popular.MovieRepositoryImpl
import com.souzaemerson.data.repository.movie.popular.MovieRepository
import org.koin.dsl.module

val movieRepositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }
}