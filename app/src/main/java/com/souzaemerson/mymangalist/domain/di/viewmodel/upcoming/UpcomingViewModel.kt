package com.souzaemerson.mymangalist.domain.di.viewmodel.upcoming

import com.souzaemerson.mymangalist.presentation.fragment.upcoming.viewmodel.UpcomingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val upcomingViewModelModule = module {
    viewModel {
        UpcomingViewModel(useCase = get(), ioDispatcher = get())
    }
}