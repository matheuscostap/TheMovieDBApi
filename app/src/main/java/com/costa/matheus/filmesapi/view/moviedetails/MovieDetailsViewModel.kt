package com.costa.matheus.filmesapi.view.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.costa.matheus.filmesapi.model.AbstractModel
import com.costa.matheus.filmesapi.model.response.CatalogueResponse
import com.costa.matheus.filmesapi.model.PopularResponseModel
import com.costa.matheus.filmesapi.repository.MovieRepository

class MovieDetailsViewModel (private val repository: MovieRepository): ViewModel(){

    val event = MutableLiveData<AbstractModel<CatalogueResponse>>()
    val similarsEvent = MutableLiveData<AbstractModel<PopularResponseModel>>()

    fun getGenres(){
        repository.getGenres {
            event.value = it
        }
    }

    fun getSimilar(id: Long){
        repository.getSimilar(id){
            similarsEvent.value = it
        }
    }
}