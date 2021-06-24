package com.costa.matheus.filmesapi.repository.login

import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.dto.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface LoginRepository {
    suspend fun getRequestToken(
        loginRequestTokenBody: LoginRequestTokenBody
    ): Deferred<AppTokenModel>

    suspend fun getAccessToken(
        getAccessTokenRequestBody: GetAccessTokenRequestBody
    ): Deferred<UserAccessTokenModel>

    suspend fun createSession(
        createSessionRequestBody: CreateSessionRequestBody
    ): Deferred<SessionModel>
}


class LoginRepositoryImpl(private val dataSource: LoginDataSource): LoginRepository {

    override suspend fun getRequestToken(
        loginRequestTokenBody: LoginRequestTokenBody) = withContext(Dispatchers.IO) {
        async {
            dataSource.getRequestToken(
                loginRequestTokenBody,
                BuildConfig.MoviesAPIKey,
                "Bearer ${BuildConfig.MoviesAPIKeyV4}"
            )
        }
    }

    override suspend fun getAccessToken(
        getAccessTokenRequestBody: GetAccessTokenRequestBody) = withContext(Dispatchers.IO) {
        async {
            dataSource.getAccessToken(
                getAccessTokenRequestBody,
                BuildConfig.MoviesAPIKey,
                "Bearer ${BuildConfig.MoviesAPIKeyV4}"
            )
        }
    }

    override suspend fun createSession(
        createSessionRequestBody: CreateSessionRequestBody) = withContext(Dispatchers.IO) {
        async {
            dataSource.createSession(
                createSessionRequestBody,
                BuildConfig.MoviesAPIKey
            )
        }
    }

}