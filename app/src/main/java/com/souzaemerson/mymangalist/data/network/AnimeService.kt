package com.souzaemerson.mymangalist.data.network

import com.google.gson.GsonBuilder
import com.souzaemerson.mymangalist.const.BASE_ANIME_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimeService {


    private fun initRetrofit(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl(BASE_ANIME_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    val service: Service = initRetrofit().create(Service::class.java)

}