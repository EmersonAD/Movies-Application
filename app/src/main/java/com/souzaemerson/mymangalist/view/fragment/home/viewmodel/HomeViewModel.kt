package com.souzaemerson.mymangalist.view.fragment.home.viewmodel

import androidx.lifecycle.*
import com.souzaemerson.mymangalist.core.state.State
import com.souzaemerson.mymangalist.data.model.movie.MovieResponse
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: MovieRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _response = MutableLiveData<State<MovieResponse>>()
    val response: LiveData<State<MovieResponse>> = _response


    fun getPopularMovies(apikey: String, language: String, page: Int) {
        viewModelScope.launch {
            try {
                _response.value = State.loading(true)

                val response = withContext(ioDispatcher) {
                    repository.getPopularMovies(apikey, language, page)
                }
                _response.value = State.loading(false)
                _response.value = State.success(response)
            } catch (throwable: Throwable) {
                _response.value = State.error(throwable)
                _response.value = State.loading(false)
            }
        }
    }

    class HomeViewModelProviderFactory(
        private val repository: MovieRepository,
        private val ioDispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown viewModel Class")
        }
    }
}