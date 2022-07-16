package com.souzaemerson.mymangalist.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pagination(
    @SerializedName("current_page")
    val current_page: Int,
    @SerializedName("has_next_page")
    val has_next_page: Boolean,
    @SerializedName("items")
    val items: Items,
    @SerializedName("last_visible_page")
    val last_visible_page: Int
) : Serializable