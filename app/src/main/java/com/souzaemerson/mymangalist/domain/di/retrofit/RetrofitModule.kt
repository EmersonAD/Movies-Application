package com.souzaemerson.mymangalist.domain.di.retrofit

import com.google.gson.GsonBuilder
import com.souzaemerson.mymangalist.const.BASE_URL
import com.souzaemerson.mymangalist.data.network.service.Service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single {
        val gson = GsonBuilder().setLenient().create()
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(Service::class.java)
    }
}