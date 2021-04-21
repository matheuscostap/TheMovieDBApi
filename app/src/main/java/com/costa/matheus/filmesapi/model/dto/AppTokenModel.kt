package com.costa.matheus.filmesapi.model.dto

data class AppTokenModel (
    val success: Boolean,
    val status_code: Int,
    val status_message: String,
    val request_token: String
)