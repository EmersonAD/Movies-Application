package com.souzaemerson.mymangalist.data.network.retrofit

import com.google.gson.GsonBuilder
import com.souzaemerson.mymangalist.const.BASE_URL
import com.souzaemerson.mymangalist.data.network.Service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private fun initRetrofit(baseUrl: String): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    val service: Service = initRetrofit(BASE_URL).create(Service::class.java)
}