package com.costa.matheus.filmesapi.repository

import com.costa.matheus.filmesapi.model.response.CatalogueResponse
import com.costa.matheus.filmesapi.model.PopularResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesDataSource{

    @GET("/3/tv/popular")
    fun getPopular(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<PopularResponseModel>

    @GET("/3/genre/tv/list")
    fun getGenres(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): Call<CatalogueResponse>

    @GET("/3/tv/{tv_id}/similar")
    fun getSimilar(
        @Path("tv_id") id: Long,
        @Query("api_key") key: String,
        @Query("language") language: String
    ): Call<PopularResponseModel>
}