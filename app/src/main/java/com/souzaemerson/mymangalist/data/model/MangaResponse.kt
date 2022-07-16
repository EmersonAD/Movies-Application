package com.souzaemerson.mymangalist.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MangaResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("pagination")
    val pagination: Pagination
) : Serializable