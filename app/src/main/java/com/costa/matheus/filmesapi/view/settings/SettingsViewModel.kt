package com.costa.matheus.filmesapi.view.settings

import com.costa.matheus.filmesapi.model.dto.AccountModel
import com.costa.matheus.filmesapi.model.dto.SessionModel
import com.costa.matheus.filmesapi.repository.settings.LocalDataRepository
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.view.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception

class SettingsViewModel(
    private val repository: LocalDataRepository) : BaseViewModel() {

    private val getSessionPrivateState = MutableStateFlow<RequestState<SessionModel>>(RequestState.Success(null))
    val getSessionState: StateFlow<RequestState<SessionModel>> get() = getSessionPrivateState

    private val getAccountPrivateState = MutableStateFlow<RequestState<AccountModel>>(RequestState.Success(null))
    val getAccountState: StateFlow<RequestState<AccountModel>> get() = getAccountPrivateState


    fun getSession() {
        try {
            val session = repository.getSession()
            getSessionPrivateState.value = RequestState.Success(session)
        }catch (e: Exception) {
            getSessionPrivateState.value = RequestState.Error(e, false)
        }
    }

    fun getAccount() {
        try {
            val account = repository.getAccount()
            getAccountPrivateState.value = RequestState.Success(account)
        }catch (e: Exception) {
            getAccountPrivateState.value = RequestState.Error(e, false)
        }
    }

}