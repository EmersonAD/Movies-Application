package com.souzaemerson.mymangalist.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Trailer(
    @SerializedName("embed_url")
    val embed_url: String,
    @SerializedName("images")
    val images: ImagesX,
    @SerializedName("url")
    val url: String,
    @SerializedName("youtube_id")
    val youtube_id: String
) : Serializable