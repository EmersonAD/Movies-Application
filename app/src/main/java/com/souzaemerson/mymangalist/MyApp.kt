package com.souzaemerson.mymangalist

import android.app.Application
import com.souzaemerson.mymangalist.domain.di.dispatcher.dispatcherModule
import com.souzaemerson.mymangalist.domain.di.getmovie.getMoviesUseCaseModule
import com.souzaemerson.domain.di.getmovie.movieRepositoryModule
import com.souzaemerson.mymangalist.domain.di.retrofit.apiModule
import com.souzaemerson.mymangalist.domain.di.searchmovie.searchForMoviesRepositoryModule
import com.souzaemerson.mymangalist.domain.di.searchmovie.searchForMoviesUseCaseModule
import com.souzaemerson.mymangalist.domain.di.viewmodel.homeViewModelModule
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
        dispatcherModule,
        movieRepositoryModule,
        getMoviesUseCaseModule,
        apiModule,
        searchForMoviesRepositoryModule,
        searchForMoviesUseCaseModule,
        homeViewModelModule
    )
}