package com.souzaemerson.mymangalist.data.model.movie

import com.souzaemerson.data.model.movie.Result

data class MovieResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)