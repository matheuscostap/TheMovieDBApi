package com.costa.matheus.filmesapi.view.searchresults

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.costa.matheus.filmesapi.model.dto.MovieModel
import com.costa.matheus.filmesapi.repository.searchresults.SearchResultsPagingSource
import com.costa.matheus.filmesapi.repository.searchresults.SearchResultsRepository
import com.costa.matheus.filmesapi.view.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class SearchResultsViewModel (
    private val repository: SearchResultsRepository) : BaseViewModel() {

    private var pagingSource: SearchResultsPagingSource? = null


    fun searchByGenre(genreId: Long): Flow<PagingData<MovieModel>> {
        pagingSource = SearchResultsPagingSource(repository, genreId)
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSource!! }
        ).flow.cachedIn(viewModelScope)
    }

    fun reloadList() = pagingSource?.invalidate()
}