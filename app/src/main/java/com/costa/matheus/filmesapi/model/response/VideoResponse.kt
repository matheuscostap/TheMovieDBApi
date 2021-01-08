package com.costa.matheus.filmesapi.model.response

import com.costa.matheus.filmesapi.model.dto.VideoModel

data class VideoResponse(
    val results: List<VideoModel>
)