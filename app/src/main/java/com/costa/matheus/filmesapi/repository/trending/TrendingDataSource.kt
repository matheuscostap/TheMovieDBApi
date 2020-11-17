package com.costa.matheus.filmesapi.repository.trending

import com.costa.matheus.filmesapi.model.response.TrendingResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingDataSource {

    @GET("/3/trending/movie/week")
    suspend fun getTrending(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): TrendingResponse

}