package com.costa.matheus.filmesapi.repository.searchresults

import androidx.paging.PagingSource
import com.costa.matheus.filmesapi.model.dto.MovieModel

class SearchResultsPagingSource(
    private val repository: SearchResultsRepository,
    private val genreId: Long): PagingSource<Int, MovieModel>() {

    private var pageKey = 1


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        try {

            val response = repository.searchByGenre(pageKey, genreId).await()
            pageKey++

            return LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = pageKey
            )

        }catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}