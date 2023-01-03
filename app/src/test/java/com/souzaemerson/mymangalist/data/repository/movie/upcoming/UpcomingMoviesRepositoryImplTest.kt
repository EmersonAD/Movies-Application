package com.souzaemerson.mymangalist.data.repository.movie.upcoming

import com.google.common.truth.Truth
import com.souzaemerson.const.LANGUAGE
import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.data.network.service.Service
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class UpcomingMoviesRepositoryImplTest {

    private val apiService = Mockito.mock(Service::class.java)
    private val subject = UpcomingMoviesRepositoryImpl(apiService)

    @Test
    fun `should return correctly`() = runTest {
        //Arrange
        val parameter = MovieResponse(1, listOf(), 1, 1)
        val expected = Response.success<MovieResponse>(200, parameter)

        Mockito.`when`(apiService.getUpcomingMovies("", LANGUAGE, 1)).thenReturn(expected)

        //Act
        val result = subject.getUpcomingMovies("", LANGUAGE, 1)

        //Assert
        Truth.assertThat(result).isEqualTo(expected)
    }
}