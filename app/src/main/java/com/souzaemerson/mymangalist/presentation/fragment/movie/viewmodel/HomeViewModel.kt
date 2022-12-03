package com.souzaemerson.mymangalist.presentation.fragment.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.usecase.search.SearchForMoviesUseCase
import com.souzaemerson.state.State
import com.souzaemerson.state.State.Companion.error
import com.souzaemerson.state.State.Companion.loading
import com.souzaemerson.state.State.Companion.success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getMovies: com.souzaemerson.domain.usecase.getmovie.GetMoviesContentUseCase,
    private val searchMovie: SearchForMoviesUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _response = MutableLiveData<State<List<ResultDomain>>>()
    val response: LiveData<State<List<ResultDomain>>> = _response

    private var _search = MutableLiveData<State<List<ResultDomain>>>()
    val search: LiveData<State<List<ResultDomain>>> = _search

    fun getPopularMovies(page: Int, apikey: String) {
        viewModelScope.launch {
            try {
                _response.value = loading(true)

                withContext(ioDispatcher) {
                    getMovies(page, apikey)
                }.let { movies ->
                    _response.value = loading(false)
                    _response.value = success(movies)
                }
            } catch (throwable: Throwable) {
                _response.value = error(throwable)
                _response.value = loading(false)
            }
        }
    }

    fun searchForMovie(movieName: String, apikey: String) {
        viewModelScope.launch {
            try {
                _search.value = loading(true)
                withContext(ioDispatcher) {
                    searchMovie(movieName, apikey)
                }.let { movies ->
                    _search.value = success(movies)
                    _search.value = loading(false)
                }
            } catch (e: Exception) {
                _search.value = error(e)
                _search.value = loading(false)
            }
        }
    }
}