package com.souzaemerson.mymangalist.data.network

import com.souzaemerson.mymangalist.data.model.MangaResponse
import retrofit2.http.GET

interface Service {

    @GET("/top/anime")
    fun getTopAnimes(): MangaResponse

}