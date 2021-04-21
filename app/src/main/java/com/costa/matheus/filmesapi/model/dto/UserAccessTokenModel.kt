package com.costa.matheus.filmesapi.model.dto

data class UserAccessTokenModel(
    val status_message: String,
    val access_token: String,
    val success: Boolean,
    val status_code: Int,
    val account_id: String
)