package com.souzaemerson.mymangalist.domain.di.searchmovie

import com.souzaemerson.mymangalist.domain.repository.search.SearchMovieRepository
import com.souzaemerson.mymangalist.data.repository.movie.search.SearchMovieRepositoryImpl
import org.koin.dsl.module

val searchForMoviesRepositoryModule = module {
    single<SearchMovieRepository> {
        SearchMovieRepositoryImpl(get())
    }
}