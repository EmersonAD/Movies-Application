package com.souzaemerson.mymangalist.domain.usecase.search

import com.souzaemerson.const.LANGUAGE
import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomain
import com.souzaemerson.mymangalist.domain.repository.search.SearchMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
class SearchForMoviesUseCaseImplTest {

    private  var repositoryImpl = mock(SearchMovieRepository::class.java)
    private  var mapper = mock(TransformResultIntoDomain::class.java)
    private val subject = SearchForMoviesUseCaseImpl(repositoryImpl, mapper)

    @Test
    fun `should return correctly`() = runTest {
        //Arrange
        val movieResponse = MovieResponse(1, listOf(), 1, 1)
        val resultDomain = ResultDomain(1, true, listOf(), "", "", "", "", 0.0, 0.0)
        val expected = listOf(resultDomain)

        Mockito.`when`(repositoryImpl.searchMovie("", LANGUAGE, 1, "")).thenReturn(Response.success(200, movieResponse))
        Mockito.`when`(mapper.transform(Mockito.anyList())).thenReturn(expected)

        //Act
        val result = subject.invoke("", "")

        //Assert
        assertEquals(expected, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw an exception when body null`() = runTest {
        //Arrange
        val response = Response.success<MovieResponse>(200, null)
        val expected = IllegalArgumentException("Response body cannot be null")

        Mockito.`when`(repositoryImpl.searchMovie("", LANGUAGE, 1, "")).thenReturn(response)

        //Act
        val result = subject.invoke("", "")

        //Assert
        assertEquals(expected, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw an exception when response code is 400 at 500`() = runTest {
        //Arrange
        val response = Response.error<MovieResponse>(400, "".toResponseBody())
        val expected = IllegalArgumentException()

        Mockito.`when`(repositoryImpl.searchMovie("", LANGUAGE, 1, "")).thenReturn(response)

        //Act
        val result = subject.invoke("", "")

        //Assert
        assertEquals(expected, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw an exception when else scenario`() = runTest {
        //Arrange
        val expected = IllegalArgumentException()

        Mockito.`when`(repositoryImpl.searchMovie("", LANGUAGE, 1, "")).thenThrow(expected)

        //Act
        val result = subject.invoke("", "")

        //Assert
        assertEquals(expected, result)
    }
}