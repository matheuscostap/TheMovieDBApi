package com.costa.matheus.filmesapi.model

import com.costa.matheus.filmesapi.model.dto.MovieModel

data class PopularResponseModel(
    val page: Int,
    val total_results: Int,
    val results: List<MovieModel>
)