package com.costa.matheus.filmesapi.repository.account

import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.dto.AccountModel
import com.costa.matheus.filmesapi.utils.Constants
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface AccountRepository {
    suspend fun getAccount(): Deferred<AccountModel>
}


class AccountRepositoryImpl(private val dataSource: AccountDataSource): AccountRepository {

    override suspend fun getAccount() = withContext(Dispatchers.IO) {
        async {
            dataSource.getAccount(
                BuildConfig.MoviesAPIKey,
                Hawk.get(Constants.sessionIdKey)
            )
        }
    }

}