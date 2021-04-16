package com.costa.matheus.filmesapi.repository.login

import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.dto.AppTokenModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface LoginRepository {
    suspend fun getRequestToken(): Deferred<AppTokenModel>
}


class LoginRepositoryImpl(private val dataSource: LoginDataSource): LoginRepository {

    override suspend fun getRequestToken() = withContext(Dispatchers.IO) {
        async {
            dataSource.getRequestToken(BuildConfig.MoviesAPIKey)
        }
    }

}