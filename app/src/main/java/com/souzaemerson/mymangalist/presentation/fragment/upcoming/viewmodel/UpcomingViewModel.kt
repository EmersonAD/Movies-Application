package com.souzaemerson.mymangalist.presentation.fragment.upcoming.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souzaemerson.mymangalist.domain.mapper.UpcomingDomain
import com.souzaemerson.mymangalist.domain.usecase.upcoming.GetUpcomingMoviesUseCase
import com.souzaemerson.state.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpcomingViewModel(
    private val useCase: GetUpcomingMoviesUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _upcoming = MutableLiveData<State<List<UpcomingDomain>>>()
    val upcoming: LiveData<State<List<UpcomingDomain>>> = _upcoming

    fun getUpcomingMovies(apikey: String, language: String = "pt-BR", page: Int) {
        viewModelScope.launch {
            try {
                _upcoming.value = State.loading(true)
                withContext(ioDispatcher) {
                    useCase(apikey, language, page)
                }.let { upcomingMovies ->
                    _upcoming.value = State.success(upcomingMovies)
                    _upcoming.value = State.loading(false)
                }
            } catch (e: Exception) {
                _upcoming.value = State.error(e)
                _upcoming.value = State.loading(false)
            }
        }
    }
}