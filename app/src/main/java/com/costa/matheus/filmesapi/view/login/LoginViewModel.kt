package com.costa.matheus.filmesapi.view.login

import com.costa.matheus.filmesapi.model.dto.AppTokenModel
import com.costa.matheus.filmesapi.repository.login.LoginRepository
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.view.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val repository: LoginRepository): BaseViewModel() {

    private val privateState = MutableStateFlow<RequestState<AppTokenModel>>(RequestState.Success(null))
    val state: StateFlow<RequestState<AppTokenModel>> get() = privateState


    fun getRequestToken() {
        jobs add launch {
            privateState.value = RequestState.Loading

            try{
                val response = repository.getRequestToken().await()
                privateState.value = RequestState.Success(response)
            }catch (t: Throwable) {
                privateState.value = RequestState.Error(t, false)
            }
        }
    }

}