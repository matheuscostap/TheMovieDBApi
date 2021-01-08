package com.costa.matheus.filmesapi.repository.moviedetails

import com.costa.matheus.filmesapi.model.dto.MovieDetailModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsDataSource {

    @GET("/3/movie/{movieId}")
    suspend fun getMovie(
        @Path("movieId") movieId: Long,
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String
    ): MovieDetailModel
}