package com.costa.matheus.filmesapi.repository

import android.util.Log
import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.AbstractModel
import com.costa.matheus.filmesapi.model.response.CatalogueResponse
import com.costa.matheus.filmesapi.model.PopularResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface MovieRepository{
    fun getPopular(page: Int, callback: (response: AbstractModel<PopularResponseModel>) -> Unit)
    fun getGenres(callback: (response: AbstractModel<CatalogueResponse>) -> Unit)
    fun getSimilar(id: Long, callback: (response: AbstractModel<PopularResponseModel>) -> Unit)
}

class MovieRepositoryImpl(private val datasource: MoviesDataSource) : MovieRepository{

    override fun getPopular(page: Int, callback: (response: AbstractModel<PopularResponseModel>) -> Unit) {
        callback(AbstractModel(isLoading = true))
        datasource.getPopular(BuildConfig.MoviesAPIKey, "pt-BR", page).enqueue(object : Callback<PopularResponseModel>{
            override fun onFailure(call: Call<PopularResponseModel>?, t: Throwable?) {
                callback(AbstractModel(error = t))
            }

            override fun onResponse(call: Call<PopularResponseModel>?, response: Response<PopularResponseModel>?) {
                response?.let {
                    if (response.code() == 200){
                        Log.i("MovieRepository","onResponse() success body -> ${it.body()}")
                        callback(AbstractModel(isSuccess = true, obj = it.body()))
                    }else{
                        callback(AbstractModel(error = Throwable()))
                    }
                }
            }
        })
    }

    override fun getGenres(callback: (response: AbstractModel<CatalogueResponse>) -> Unit) {
        callback(AbstractModel(isLoading = true))
        datasource.getGenres(BuildConfig.MoviesAPIKey, "pt-BR").enqueue(object : Callback<CatalogueResponse>{
            override fun onFailure(call: Call<CatalogueResponse>?, t: Throwable?) {
                callback(AbstractModel(error = t))
            }

            override fun onResponse(call: Call<CatalogueResponse>?, response: Response<CatalogueResponse>?) {
                response?.let {
                    if (response.code() == 200){
                        Log.i("MovieRepository","onResponse() success body -> ${it.body()}")
                        callback(AbstractModel(isSuccess = true, obj = it.body()))
                    }else{
                        callback(AbstractModel(error = Throwable()))
                    }
                }
            }
        })
    }

    override fun getSimilar(id: Long, callback: (response: AbstractModel<PopularResponseModel>) -> Unit) {
        callback(AbstractModel(isLoading = true))
        datasource.getSimilar(id, BuildConfig.MoviesAPIKey, "pt-BR").enqueue(object : Callback<PopularResponseModel>{
            override fun onFailure(call: Call<PopularResponseModel>?, t: Throwable?) {
                callback(AbstractModel(error = t))
            }

            override fun onResponse(call: Call<PopularResponseModel>?, response: Response<PopularResponseModel>?) {
                response?.let {
                    if (response.code() == 200){
                        Log.i("MovieRepository","onResponse() success body -> ${it.body()}")
                        callback(AbstractModel(isSuccess = true, obj = it.body()))
                    }else{
                        callback(AbstractModel(error = Throwable()))
                    }
                }
            }
        })
    }

}