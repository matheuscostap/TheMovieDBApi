package com.costa.matheus.filmesapi.repository.account

import com.costa.matheus.filmesapi.model.dto.AccountModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountDataSource {

    @GET("/3/account")
    suspend fun getAccount(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): AccountModel

}