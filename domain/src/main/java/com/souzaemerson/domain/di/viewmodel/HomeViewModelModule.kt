package com.souzaemerson.domain.di.viewmodel

import com.souzaemerson.mymangalist.presentation.fragment.movie.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel {
        HomeViewModel(getMovies = get(), searchMovie = get(), ioDispatcher = get())
    }
}