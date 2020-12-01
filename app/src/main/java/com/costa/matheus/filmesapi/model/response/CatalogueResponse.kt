package com.costa.matheus.filmesapi.model.response

import com.costa.matheus.filmesapi.model.dto.GenreModel

data class CatalogueResponse(
    val genres: List<GenreModel>
)