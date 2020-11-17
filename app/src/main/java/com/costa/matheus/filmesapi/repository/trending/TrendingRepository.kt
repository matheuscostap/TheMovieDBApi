package com.costa.matheus.filmesapi.repository.trending

import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.response.TrendingResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface TrendingRepository {
    suspend fun getTrending(): Deferred<TrendingResponse>
}

class TrendingRepositoryImpl(private val dataSource: TrendingDataSource) : TrendingRepository {

    override suspend fun getTrending() = withContext(Dispatchers.IO){
        async {
            dataSource.getTrending(BuildConfig.MoviesAPIKey, "pt-BR")
        }
    }

}