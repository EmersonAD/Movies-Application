package com.souzaemerson.mymangalist

import android.app.Application
import com.souzaemerson.mymangalist.domain.di.UpcomingUseCaseModule.upcomingMoviesRepository
import com.souzaemerson.mymangalist.domain.di.UpcomingUseCaseModule.upcomingMoviesUseCase
import com.souzaemerson.mymangalist.domain.di.dispatcher.dispatcherModule
import com.souzaemerson.mymangalist.domain.di.getmovie.getMoviesUseCaseModule
import com.souzaemerson.mymangalist.domain.di.getmovie.movieRepositoryModule
import com.souzaemerson.mymangalist.domain.di.retrofit.apiModule
import com.souzaemerson.mymangalist.domain.di.searchmovie.searchForMoviesRepositoryModule
import com.souzaemerson.mymangalist.domain.di.searchmovie.searchForMoviesUseCaseModule
import com.souzaemerson.mymangalist.domain.di.viewmodel.home.homeViewModelModule
import com.souzaemerson.mymangalist.domain.di.viewmodel.upcoming.upcomingViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(getModules())
        }
    }

    private fun getModules() = listOf(
        apiModule,
        movieRepositoryModule,
        getMoviesUseCaseModule,
        homeViewModelModule,
        searchForMoviesRepositoryModule,
        searchForMoviesUseCaseModule,
        upcomingViewModelModule,
        upcomingMoviesRepository,
        upcomingMoviesUseCase,
        dispatcherModule
    )
}