package com.costa.matheus.filmesapi.model.dto

import java.io.Serializable

data class MovieModel(
    val id: Long,
    val name: String,
    val popularity: Double,
    val vote_average: Double,
    val overview: String,
    val first_air_date: String,
    val poster_path: String,
    val backdrop_path: String,
    val genre_ids: List<Long>
): Serializable