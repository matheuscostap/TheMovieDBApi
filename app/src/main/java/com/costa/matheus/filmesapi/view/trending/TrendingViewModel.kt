package com.costa.matheus.filmesapi.view.trending


import androidx.lifecycle.viewModelScope
import com.costa.matheus.filmesapi.model.response.TrendingResponse
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.repository.trending.TrendingRepository
import com.costa.matheus.filmesapi.view.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class TrendingViewModel (
    private val repository: TrendingRepository): BaseViewModel() {

    private val privateState = MutableStateFlow<RequestState<TrendingResponse>>(RequestState.Success(null))
    val state: StateFlow<RequestState<TrendingResponse>> get() = privateState


    fun getTrending() {
        jobs add launch {
            privateState.value = RequestState.Loading

            try {
                val response = repository.getTrending().await()
                privateState.value = RequestState.Success(response)
            } catch (t: Throwable) {
                privateState.value = RequestState.Error(t, false)
            }
        }
    }

}