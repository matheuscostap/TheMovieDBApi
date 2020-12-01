package com.costa.matheus.filmesapi.repository.catalogue

import com.costa.matheus.filmesapi.model.response.CatalogueResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CatalogueDataSource {

    @GET("/3/genre/movie/list")
    suspend fun getCatalogue(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): CatalogueResponse
}