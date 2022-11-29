package com.souzaemerson.mymangalist.presentation.fragment.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.usecase.GetMoviesContentUseCase
import com.souzaemerson.state.State
import com.souzaemerson.state.State.Companion.error
import com.souzaemerson.state.State.Companion.loading
import com.souzaemerson.state.State.Companion.success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getMovies: GetMoviesContentUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _response = MutableLiveData<State<List<ResultDomain>>>()
    val response: LiveData<State<List<ResultDomain>>> = _response

    fun getPopularMovies(page: Int) {
        viewModelScope.launch {
            try {
                _response.value = loading(true)

                withContext(ioDispatcher) {
                    getMovies(page)
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
}