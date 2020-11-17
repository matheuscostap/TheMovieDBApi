package com.costa.matheus.filmesapi.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.costa.matheus.filmesapi.model.AbstractModel
import com.costa.matheus.filmesapi.model.dto.MovieModel
import com.costa.matheus.filmesapi.model.PopularResponseModel

class MoviesKeyedDataSource(private val repository: MovieRepository): ItemKeyedDataSource<Int, MovieModel>() {

    private var page = 0
    val event = MutableLiveData<AbstractModel<PopularResponseModel>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<MovieModel>) {
        repository.getPopular(params.requestedInitialKey ?: 1){
            event.postValue(it)
            if (it.isSuccess){
                it.obj?.let {response ->
                    Log.i("MoviesKeyedDS","loadinitial() success")
                    page = response.page + 1
                    Log.i("MoviesKeyedDS","loadinitial() results -> ${response.results.size} - ${response.results}")
                    callback.onResult(response.results)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<MovieModel>) {
        repository.getPopular(params.key ?: 1){
            event.postValue(it)
            if (it.isSuccess){
                it.obj?.let {response ->
                    Log.i("MoviesKeyedDS","loadafter() success")
                    page = response.page + 1
                    callback.onResult(response.results)
                }
            }
        }
    }

    override fun getKey(item: MovieModel): Int {
        return page
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<MovieModel>) {}
}