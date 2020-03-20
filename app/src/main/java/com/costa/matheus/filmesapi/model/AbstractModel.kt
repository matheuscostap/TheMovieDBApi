package com.costa.matheus.filmesapi.model

data class AbstractModel<T>(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: Throwable? = null,
    val obj: T? = null
)