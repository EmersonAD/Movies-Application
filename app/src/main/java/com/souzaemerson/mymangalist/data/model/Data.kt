package com.souzaemerson.mymangalist.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("aired")
    val aired: Aired,
    @SerializedName("airing")
    val airing: Boolean,
    @SerializedName("background")
    val background: String,
    @SerializedName("broadcast")
    val broadcast: Broadcast,
    @SerializedName("demographics")
    val demographics: List<Demographic>,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("episodes")
    val episodes: Int,
    @SerializedName("explicit_genres")
    val explicit_genres: List<Any>,
    @SerializedName("favorites")
    val favorites: Int,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("images")
    val images: Images,
    @SerializedName("licensors")
    val licensors: List<Licensor>,
    @SerializedName("mal_id")
    val mal_id: Int,
    @SerializedName("members")
    val members: Int,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("producers")
    val producers: List<Producer>,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("score")
    val score: Double,
    @SerializedName("score_by")
    val scored_by: Int,
    @SerializedName("season")
    val season: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("studios")
    val studios: List<Studio>,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("themes")
    val themes: List<Theme>,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_english")
    val title_english: String,
    @SerializedName("title_japanese")
    val title_japanese: String,
    @SerializedName("title_synonyms")
    val title_synonyms: List<String>,
    @SerializedName("trailer")
    val trailer: Trailer,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("year")
    val year: Int
) : Serializable