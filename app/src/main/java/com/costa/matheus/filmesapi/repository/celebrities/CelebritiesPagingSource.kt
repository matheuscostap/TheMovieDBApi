package com.costa.matheus.filmesapi.repository.celebrities

import androidx.paging.PagingSource
import com.costa.matheus.filmesapi.model.dto.CelebrityModel
import com.costa.matheus.filmesapi.model.response.CelebritiesResponse
import java.lang.Exception

class CelebritiesPagingSource (
    private val repository: CelebritiesRepository): PagingSource<Int, CelebrityModel>() {

    private var pageKey = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CelebrityModel> {
        try {

            val response = repository.getCelebrities(pageKey).await()
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