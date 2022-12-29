package com.souzaemerson.mymangalist.presentation.fragment.movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.souzaemerson.const.LANGUAGE
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.usecase.getmovie.GetMoviesContentUseCase
import com.souzaemerson.mymangalist.domain.usecase.search.SearchForMoviesUseCase
import com.souzaemerson.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()
    private val getMoviesUseCase = mock(GetMoviesContentUseCase::class.java)
    private val searchMovieUseCase = mock(SearchForMoviesUseCase::class.java)
    private val ioDispatcher = Dispatchers.IO

    private val subject = HomeViewModel(getMoviesUseCase, searchMovieUseCase, ioDispatcher)

    private val responseLiveData = mock(Observer::class.java) as Observer<State<List<ResultDomain>>>

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should livedata change correctly when useCase is called`() = runTest {
        //Arrange
        subject.response.observeForever(responseLiveData)

        `when`(getMoviesUseCase.domains(1, "", LANGUAGE)).thenReturn(listDomain)

        //Act
        subject.getPopularMovies(1, "")

        //Assert
        verify(responseLiveData).onChanged(State.loading(true))
        verify(responseLiveData).onChanged(State.success(listDomain))
        verify(responseLiveData).onChanged(State.loading(false))
    }

    @Test(expected = Exception::class)
    fun `should livedata change correctly when useCase is`() = runTest {
        //Arrange
        val exception = Exception()
        subject.response.observeForever(responseLiveData)
        `when`(getMoviesUseCase.domains(1, "", LANGUAGE)).thenThrow(exception)

        //Act
        subject.getPopularMovies(1, "")

        //Assert
        verify(responseLiveData).onChanged(State.loading(true))
        verify(responseLiveData).onChanged(State.error(exception))
        verify(responseLiveData).onChanged(State.loading(false))
    }


    private val resultDomain = ResultDomain(
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

    private val listDomain = listOf(resultDomain)

}