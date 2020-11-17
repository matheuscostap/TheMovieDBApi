package com.costa.matheus.filmesapi.model.response

import com.costa.matheus.filmesapi.model.dto.MovieModel

data class TrendingResponse (
    val page: Int,
    val results: List<MovieModel>
)