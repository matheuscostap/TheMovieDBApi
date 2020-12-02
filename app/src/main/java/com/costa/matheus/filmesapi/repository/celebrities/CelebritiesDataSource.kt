package com.costa.matheus.filmesapi.repository.celebrities

import com.costa.matheus.filmesapi.model.response.CelebritiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CelebritiesDataSource {

    @GET("/3/person/popular")
    suspend fun getCelebrities(
        @Query("page") page: Int,
        @Query("api_key") key: String,
        @Query("language") language: String
    ): CelebritiesResponse

}