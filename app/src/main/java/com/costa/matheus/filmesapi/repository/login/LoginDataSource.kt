package com.costa.matheus.filmesapi.repository.login

import com.costa.matheus.filmesapi.model.dto.*
import retrofit2.http.*

interface LoginDataSource {

    @POST("/4/auth/request_token")
    suspend fun getRequestToken(
        @Body loginRequestTokenBody: LoginRequestTokenBody,
        @Query("api_key") key: String,
        @Header("Authorization") appTokenV4: String
    ): AppTokenModel

    @POST("/4/auth/access_token")
    suspend fun getAccessToken(
        @Body loginRequestTokenBody: GetAccessTokenRequestBody,
        @Query("api_key") key: String,
        @Header("Authorization") appTokenV4: String
    ): UserAccessTokenModel

    @POST("/3/authentication/session/convert/4")
    suspend fun createSession(
        @Body createSessionRequestBody: CreateSessionRequestBody,
        @Query("api_key") key: String
    ): SessionModel

}