package com.costa.matheus.filmesapi.model.dto

import com.costa.matheus.filmesapi.model.response.VideoResponse

data class MovieDetailModel(
    val adult: Boolean,
    val backdrop_path: String,
    val genres: List<GenreModel>,
    val homepage: String,
    val id: Long,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val tagline: String,
    val title: String,
    val vote_average: Float,
    val videos: VideoResponse
)