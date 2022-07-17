package com.souzaemerson.mymangalist.data.model.anime

data class AnimeResponse(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta,
    val pagination: Pagination
)