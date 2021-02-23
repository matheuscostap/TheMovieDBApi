package com.costa.matheus.filmesapi.model.dto

data class CastModel(
    val adult: Boolean,
    val gender: Int,
    val id: Long,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val order: Int
)