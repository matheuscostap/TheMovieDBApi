package com.costa.matheus.filmesapi.model.dto

data class AvatarModel(
    val gravatar: GravatarModel,
    val tmdb: TmdbAvatarModel
)