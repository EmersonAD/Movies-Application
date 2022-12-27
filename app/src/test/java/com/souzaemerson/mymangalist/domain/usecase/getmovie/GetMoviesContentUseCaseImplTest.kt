package com.souzaemerson.mymangalist.domain.usecase.getmovie

import com.google.common.truth.Truth
import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomain
import com.souzaemerson.mymangalist.domain.repository.popular.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
class GetMoviesContentUseCaseImplTest {

    private val repositoryMock = mock(MovieRepository::class.java)
    private val mapper = mock(TransformResultIntoDomain::class.java)
    private val subject = GetMoviesContentUseCaseImpl(repositoryMock, mapper)

    @Test
    fun `should return domain list when response is 200 and not null`() = runTest {
        //Arrange
        val movieResponse = MovieResponse(1, listOf(), 1, 1)
        val resultDomain = ResultDomain(
            id = 1,
            adult = false,
            genre_ids = listOf(),
            title = "title",
            overview = "overview",
            poster_path = "",
            release_date = "",
            vote_average = 0.0,
            popularity = 0.0
        )
        val responseMock = Response.success(200, movieResponse)
        val expected = listOf(resultDomain)

        `when`(repositoryMock.getPopularMovies("", "", 1)).thenReturn(responseMock)
        `when`(mapper.transform(listOf())).thenReturn(expected)

        //Act
        val result = subject.domains(1, "", "")

        //Assert
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun `should throw an exception when response code 200 and null`() = runTest {
        //Arrange
        val responseMock = Response.success<MovieResponse>(200, null)
        val expected = Exception("Response body cannot be null")

        `when`(repositoryMock.getPopularMovies("", "", 1)).thenReturn(responseMock)

        //Act
        val result = subject.domains(1, "", "")

        //Assert
        Truth.assertThat(result).isEqualTo(expected)
    }

    //TODO: Fazer cenário do else quando for refatorado no useCase
}