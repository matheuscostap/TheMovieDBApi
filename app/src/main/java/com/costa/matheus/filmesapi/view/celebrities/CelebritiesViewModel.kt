package com.costa.matheus.filmesapi.view.celebrities

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.costa.matheus.filmesapi.model.dto.CelebrityModel
import com.costa.matheus.filmesapi.repository.celebrities.CelebritiesPagingSource
import com.costa.matheus.filmesapi.repository.celebrities.CelebritiesRepository
import com.costa.matheus.filmesapi.view.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class CelebritiesViewModel (
    private val repository: CelebritiesRepository): BaseViewModel() {

    fun getCelebrities(): Flow<PagingData<CelebrityModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { CelebritiesPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }

}