package com.costa.matheus.filmesapi.repository.celebrities

import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.response.CelebritiesResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface CelebritiesRepository {
    suspend fun getCelebrities(page: Int): Deferred<CelebritiesResponse>
}

class CelebritiesRepositoryImpl(private val dataSource: CelebritiesDataSource) : CelebritiesRepository {

    override suspend fun getCelebrities(page: Int) = withContext(Dispatchers.IO) {
        async {
            dataSource.getCelebrities(page, BuildConfig.MoviesAPIKey, "pt-BR")
        }
    }

}