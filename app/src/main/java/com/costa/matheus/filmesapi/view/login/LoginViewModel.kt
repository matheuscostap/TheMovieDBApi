package com.costa.matheus.filmesapi.view.login

import com.costa.matheus.filmesapi.model.dto.*
import com.costa.matheus.filmesapi.repository.account.AccountRepository
import com.costa.matheus.filmesapi.repository.login.LoginRepository
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.utils.Constants
import com.costa.matheus.filmesapi.view.base.BaseViewModel
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val loginRepository: LoginRepository,
    private val accountRepository: AccountRepository): BaseViewModel() {

    private val getRequestTokenPrivateState = MutableStateFlow<RequestState<AppTokenModel>>(RequestState.Success(null))
    val getRequestTokenState: StateFlow<RequestState<AppTokenModel>> get() = getRequestTokenPrivateState

    private val getAccessTokenPrivateState = MutableStateFlow<RequestState<UserAccessTokenModel>>(RequestState.Success(null))
    val getAccessTokenState: StateFlow<RequestState<UserAccessTokenModel>> get() = getAccessTokenPrivateState

    private val createSessionPrivateState = MutableStateFlow<RequestState<SessionModel>>(RequestState.Success(null))
    val createSessionState: StateFlow<RequestState<SessionModel>> get() = createSessionPrivateState

    private val getAccountPrivateState = MutableStateFlow<RequestState<AccountModel>>(RequestState.Success(null))
    val getAccountState: StateFlow<RequestState<AccountModel>> get() = getAccountPrivateState


    fun getRequestToken(loginRequestTokenBody: LoginRequestTokenBody) {
        jobs add launch {
            getRequestTokenPrivateState.value = RequestState.Loading

            try{
                val response = loginRepository.getRequestToken(loginRequestTokenBody).await()
                getRequestTokenPrivateState.value = RequestState.Success(response)
            }catch (t: Throwable) {
                getRequestTokenPrivateState.value = RequestState.Error(t, false)
            }
        }
    }

    fun getAccessToken(getAccessTokenRequestBody: GetAccessTokenRequestBody) {
        jobs add launch {
            getAccessTokenPrivateState.value = RequestState.Loading

            try{
                val response = loginRepository.getAccessToken(getAccessTokenRequestBody).await()
                getAccessTokenPrivateState.value = RequestState.Success(response)
            }catch (t: Throwable) {
                getAccessTokenPrivateState.value = RequestState.Error(t, false)
            }
        }
    }

    fun createSession(createSessionRequestBody: CreateSessionRequestBody) {
        jobs add launch {
            createSessionPrivateState.value = RequestState.Loading

            try{
                val response = loginRepository.createSession(createSessionRequestBody).await()
                createSessionPrivateState.value = RequestState.Success(response)
            }catch (t: Throwable) {
                createSessionPrivateState.value = RequestState.Error(t, false)
            }
        }
    }

    fun getAccount() {
        jobs add launch {
            getAccountPrivateState.value = RequestState.Loading

            try{
                val response = accountRepository.getAccount().await()
                getAccountPrivateState.value = RequestState.Success(response)
            }catch (t: Throwable) {
                getAccountPrivateState.value = RequestState.Error(t, false)
            }
        }
    }

    fun saveRequestToken(token: AppTokenModel): Boolean {
        return Hawk.put(Constants.requestTokenKey, token)
    }

    fun saveAccessToken(token: UserAccessTokenModel): Boolean {
        return Hawk.put(Constants.userAccessTokenKey, token)
    }

    fun saveSession(session: SessionModel): Boolean {
        return Hawk.put(Constants.sessionIdKey, session)
    }

    fun saveAccount(account: AccountModel): Boolean {
        return Hawk.put(Constants.accountKey, account)
    }

}