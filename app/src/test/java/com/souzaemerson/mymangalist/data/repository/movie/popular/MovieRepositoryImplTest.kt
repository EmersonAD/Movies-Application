package com.souzaemerson.mymangalist.data.repository.movie.popular

import com.google.common.truth.Truth
import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.data.network.service.Service
import com.souzaemerson.mymangalist.domain.repository.popular.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    private val apiMock = mock(Service::class.java)
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setup() {
        movieRepository = MovieRepositoryImpl(apiMock)
    }

    @Test
    fun `should return a success response when called`(): Unit = runBlocking {
        //Arrange
        val expected200: Response<MovieResponse> = Response.success(200, MovieResponse(1, listOf(), 1, 1))
        `when`(apiMock.getPopularMovies("", "", 1)).thenReturn(expected200)

        //Act
        val result = movieRepository.getPopularMovies("", "")

        //Assert
        Truth.assertThat(result).isEqualTo(expected200)
        verify(apiMock).getPopularMovies("", "", 1)
    }


    @Test
    fun `should return a error response when called`(): Unit = runBlocking {
        //Arrange
        val expectedError = Response.error<MovieResponse>(400, "".toResponseBody())
        `when`(apiMock.getPopularMovies("", "", 1)).thenReturn(expectedError)

        //Act
        val result = movieRepository.getPopularMovies("", "")

        //Assert
        Truth.assertThat(result).isEqualTo(expectedError)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should return an exception when called`(): Unit = runBlocking {
        //Arrange
        val exceptionExpected = IllegalArgumentException()
        `when`(apiMock.getPopularMovies("", "", 1)).thenThrow(exceptionExpected)

        //Act
        val result = movieRepository.getPopularMovies("", "")

        //Assert
        Truth.assertThat(result).isEqualTo(exceptionExpected)
    }
}