package com.costa.matheus.filmesapi.repository.searchresults

import com.costa.matheus.filmesapi.model.response.SearchResultsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchResultsDataSource {

    @GET("/3/discover/movie")
    suspend fun serchByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Long,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") adult: Boolean = false,
        @Query("include_video") video: Boolean = true,
        @Query("api_key") key: String,
        @Query("language") language: String
    ): SearchResultsResponse
}