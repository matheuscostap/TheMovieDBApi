package com.costa.matheus.filmesapi.repository.catalogue

import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.response.CatalogueResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface CatalogueRepository {
    suspend fun getCatalogue(): Deferred<CatalogueResponse>
}

class CatalogueRepositoryImpl(private val dataSource: CatalogueDataSource): CatalogueRepository {

    override suspend fun getCatalogue() = withContext(Dispatchers.IO){
        async {
            dataSource.getCatalogue(BuildConfig.MoviesAPIKey, "pt-BR")
        }
    }

}