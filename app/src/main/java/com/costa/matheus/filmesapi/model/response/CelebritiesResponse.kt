package com.costa.matheus.filmesapi.model.response

import com.costa.matheus.filmesapi.model.dto.CelebrityModel

data class CelebritiesResponse(
    val total_results: Int,
    val page: Int,
    val total_pages: Int,
    val results: List<CelebrityModel>
)