package com.costa.matheus.filmesapi.repository.searchresults

import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.response.SearchResultsResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface SearchResultsRepository {
    suspend fun searchByGenre(page: Int, genreId: Long): Deferred<SearchResultsResponse>
}


class SearchResultsRepositoryImpl(
    private val dataSource: SearchResultsDataSource) : SearchResultsRepository {

    override suspend fun searchByGenre(page: Int, genreId: Long) = withContext(Dispatchers.IO) {
        async {
            dataSource.serchByGenre(
                page = page,
                genreId = genreId,
                key = BuildConfig.MoviesAPIKey,
                language = "pt-BR")
        }
    }

}