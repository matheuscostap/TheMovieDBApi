package com.costa.matheus.filmesapi.model.response

import com.costa.matheus.filmesapi.model.dto.MovieModel

data class SearchResultsResponse (
    val page: Int,
    val results: List<MovieModel>
)