package com.souzaemerson.mymangalist.domain.di.dispatcher

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatcherModule = module {
    single {
        return@single Dispatchers.IO
    }
}