package com.costa.matheus.filmesapi.repository.state

sealed class RequestState<out T> {

    object Loading: RequestState<Nothing>()

    data class Success<T>(val data: T?): RequestState<T>()

    data class Error(val throwable: Throwable, var consumed: Boolean): RequestState<Nothing>()
}