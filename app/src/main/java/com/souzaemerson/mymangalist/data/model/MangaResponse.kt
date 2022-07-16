package com.souzaemerson.mymangalist.data.model

data class MangaResponse(
    val `data`: List<Data>,
    val pagination: Pagination
)