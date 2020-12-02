package com.costa.matheus.filmesapi.model.dto

data class CelebrityModel(
    val id: Int,
    val adult: Boolean,
    val profile_path: String,
    val name: String,
    val gender: Int,
    val popularity: Double
)