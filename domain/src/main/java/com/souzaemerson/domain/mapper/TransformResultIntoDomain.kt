package com.souzaemerson.domain.mapper

import com.souzaemerson.mymangalist.data.model.movie.Result
import java.io.Serializable

class TransformResultIntoDomain {
    companion object {
        operator fun invoke(results: List<Result>): List<ResultDomain> {
            return results.map { result: Result ->
                ResultDomain(
                    id = result.id,
                    adult = result.adult,
                    genre_ids = result.genre_ids,
                    title = result.title,
                    poster_path = result.poster_path,
                    overview = result.overview,
                    release_date = result.release_date,
                    vote_average = result.vote_average,
                    popularity = result.popularity
                )
            }
        }
    }
}

data class ResultDomain(
    val id: Int,
    val adult: Boolean,
    val genre_ids: List<Int>,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
    val popularity: Double
): Serializable