package com.costa.matheus.filmesapi.model.dto

data class AppTokenModel (
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)