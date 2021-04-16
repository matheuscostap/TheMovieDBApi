package com.costa.matheus.filmesapi.repository.login

import com.costa.matheus.filmesapi.model.dto.AppTokenModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginDataSource {

    @GET("/3/authentication/token/new")
    suspend fun getRequestToken(
        @Query("api_key") key: String
    ): AppTokenModel
}