package com.costa.matheus.filmesapi.model.dto

data class AccountModel(
    val id: Long,
    val name: String,
    val username: String,
    val include_adult: Boolean,
    val avatar: AvatarModel
)