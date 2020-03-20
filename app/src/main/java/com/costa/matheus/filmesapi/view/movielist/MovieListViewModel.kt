package com.costa.matheus.filmesapi.view.movielist

import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.costa.matheus.filmesapi.model.AbstractModel
import com.costa.matheus.filmesapi.model.MovieModel
import com.costa.matheus.filmesapi.model.PopularResponseModel
import com.costa.matheus.filmesapi.repository.MoviesKeyedDataSource
import com.costa.matheus.filmesapi.repository.MoviesKeyedDataSourceFactory

class MovieListViewModel(private val factory: MoviesKeyedDataSourceFactory): ViewModel(){

    var moviesList: LiveData<PagedList<MovieModel>>
    var event: LiveData<AbstractModel<PopularResponseModel>>? = null

    init {
        event = Transformations.switchMap(factory.liveDataSource){
            it!!.event
        }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(20)
            .build()

        moviesList = LivePagedListBuilder(factory, pagedListConfig).build()
    }

    fun refresh(){
        factory.liveDataSource.value?.invalidate()
    }
}