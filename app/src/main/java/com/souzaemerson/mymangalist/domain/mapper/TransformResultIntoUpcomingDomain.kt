package com.souzaemerson.mymangalist.domain.mapper

import com.souzaemerson.mymangalist.data.model.movie.Result

class TransformResultIntoUpcomingDomain() {
    companion object {
        operator fun invoke(resultList: List<Result>): List<UpcomingDomain> {
            return resultList.map { result: Result ->
                UpcomingDomain(
                    title = result.title,
                    overview = result.overview,
                    image = result.poster_path ?: "",
                    adult = result.adult,
                    releaseDate = result.release_date
                )
            }
        }
    }
}

data class UpcomingDomain(
    val title: String,
    val overview: String,
    val image: String,
    val adult: Boolean,
    val releaseDate: String
)