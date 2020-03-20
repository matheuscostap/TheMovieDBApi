package com.costa.matheus.filmesapi.model

data class PopularResponseModel(
    val page: Int,
    val total_results: Int,
    val results: List<MovieModel>
)