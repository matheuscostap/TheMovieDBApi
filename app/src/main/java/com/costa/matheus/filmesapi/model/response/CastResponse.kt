package com.costa.matheus.filmesapi.model.response

import com.costa.matheus.filmesapi.model.dto.CastModel

data class CastResponse(
    val cast: List<CastModel>
)