package com.souzaemerson.mymangalist.view.fragment.anime.viewmodel

import androidx.lifecycle.*
import com.souzaemerson.mymangalist.core.state.State
import com.souzaemerson.mymangalist.data.model.anime.AnimeResponse
import com.souzaemerson.mymangalist.data.repository.anime.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimeViewModel(
    private val repository: AnimeRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _response = MutableLiveData<State<AnimeResponse>>()
    val response: LiveData<State<AnimeResponse>> = _response

    fun getTopAnimeList() {
        viewModelScope.launch {
            try {
                _response.value = State.loading(true)
                val response = withContext(ioDispatcher) {
                    repository.getAnimeList()
                }
                _response.value = State.success(response)
                _response.value = State.loading(false)
            } catch (e: Exception) {
                _response.value = State.error(e)
                _response.value = State.loading(false)
            }
        }
    }

    class AnimeViewModelProvideFactory(
        private val repository: AnimeRepository,
        private val ioDispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
                return AnimeViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unkown viewModel Class")
        }

    }
}