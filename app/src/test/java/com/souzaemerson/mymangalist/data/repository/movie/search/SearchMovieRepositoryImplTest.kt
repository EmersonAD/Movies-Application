package com.souzaemerson.mymangalist.data.repository.movie.search

import com.souzaemerson.const.LANGUAGE
import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.data.network.service.Service
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

@ExperimentalCoroutinesApi
class SearchMovieRepositoryImplTest() {

    private val apiService = mock(Service::class.java)
    private val subject = SearchMovieRepositoryImpl(apiService)

    @Test
    fun `should return correctly a response`() = runTest{
        //Arrange
        val parameter = MovieResponse(1, listOf(), 1, 1)
        val expected = Response.success(200, parameter)

        `when`(apiService.searchForMovies("", LANGUAGE, 1, "")).thenReturn(expected)

        //Act
        val result = subject.searchMovie("", LANGUAGE, 1, "")

        //Assert
        assertEquals(expected, result)
    }
}