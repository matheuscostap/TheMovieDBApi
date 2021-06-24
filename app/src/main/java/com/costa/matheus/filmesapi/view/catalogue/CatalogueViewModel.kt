package com.costa.matheus.filmesapi.view.catalogue

import com.costa.matheus.filmesapi.model.response.CatalogueResponse
import com.costa.matheus.filmesapi.repository.catalogue.CatalogueRepository
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.view.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CatalogueViewModel (
    private val repository: CatalogueRepository): BaseViewModel() {

    private val privateState = MutableStateFlow<RequestState<CatalogueResponse>>(RequestState.Success(null))
    val state: StateFlow<RequestState<CatalogueResponse>> get() = privateState


    fun getCatalogue() {
        jobs add launch {
            privateState.value = RequestState.Loading

            try {
                val response = repository.getCatalogue().await()
                privateState.value = RequestState.Success(response)
            }catch (t: Throwable) {
                privateState.value = RequestState.Error(t, false)
            }
        }
    }

}