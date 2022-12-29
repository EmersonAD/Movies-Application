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
import org.mockito.exceptions.base.MockitoException

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()
    private val getMoviesUseCase = mock(GetMoviesContentUseCase::class.java)
    private val searchMovieUseCase = mock(SearchForMoviesUseCase::class.java)
    private val ioDispatcher = Dispatchers.IO

    private val subject = HomeViewModel(getMoviesUseCase, searchMovieUseCase, ioDispatcher)

    private val responseObserver = mock(Observer::class.java) as Observer<State<List<ResultDomain>>>
    private val searchObserver = mock(Observer::class.java) as Observer<State<List<ResultDomain>>>

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should livedata change correctly when popularUseCase is called`() = runTest {
        //Arrange
        subject.response.observeForever(responseObserver)
        `when`(getMoviesUseCase.domains(1, "", LANGUAGE)).thenReturn(domainList)

        //Act
        subject.getPopularMovies(1, "")

        //Assert
        verify(responseObserver).onChanged(State.loading(true))
        verify(responseObserver).onChanged(State.success(domainList))
        verify(responseObserver).onChanged(State.loading(false))
    }

    @Test(expected = MockitoException::class)
    fun `should return an exception when popularUseCase is called`() = runTest {
        //Arrange
        subject.response.observeForever(responseObserver)
        `when`(getMoviesUseCase.domains(1, "", LANGUAGE)).thenThrow(exception)

        //Act
        subject.getPopularMovies(1, "")

        //Assert
        verify(responseObserver).onChanged(State.loading(true))
        verify(responseObserver).onChanged(State.error(exception))
        verify(responseObserver).onChanged(State.loading(false))
    }

    @Test
    fun `should return an list of result domain when searchUseCase is called`() = runTest{
        //Arrange
        subject.search.observeForever(searchObserver)
        `when`(searchMovieUseCase.invoke("", "")).thenReturn(domainList)

        //Act
        subject.searchForMovie("", "")

        //Assert
        verify(searchObserver).onChanged(State.loading(true))
        verify(searchObserver).onChanged(State.success(domainList))
        verify(searchObserver).onChanged(State.loading(false))
    }

    @Test(expected = MockitoException::class)
    fun `should throw an exception when searchUseCase is called`() = runTest{
        //Arrange
        subject.search.observeForever(searchObserver)
        `when`(searchMovieUseCase.invoke("", "")).thenThrow(exception)

        //Act
        subject.searchForMovie("", "")

        //Assert
        verify(searchObserver).onChanged(State.loading(true))
        verify(searchObserver).onChanged(State.error(exception))
        verify(searchObserver).onChanged(State.loading(false))
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

    private val domainList = listOf(resultDomain)

    private val exception = Exception()
}