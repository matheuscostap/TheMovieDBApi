package com.costa.matheus.filmesapi.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.costa.matheus.filmesapi.model.dto.MovieModel
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class MoviesKeyedDataSourceFactory(): DataSource.Factory<Int, MovieModel>(){

    val liveDataSource = MutableLiveData<MoviesKeyedDataSource>()
    var dataSource: MoviesKeyedDataSource? = null

    override fun create(): DataSource<Int, MovieModel> {
        val dataSource: MoviesKeyedDataSource = getKoinInstance()
        this.dataSource = dataSource
        liveDataSource.postValue(dataSource)

        Log.i("MoviesKeyedDSFac","create()")
        return dataSource
    }

}

inline fun <reified T : Any> getKoinInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}