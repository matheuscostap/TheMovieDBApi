package com.costa.matheus.filmesapi.view.moviedetails

import com.costa.matheus.filmesapi.model.dto.MovieDetailModel
import com.costa.matheus.filmesapi.model.response.CastResponse
import com.costa.matheus.filmesapi.model.response.VideoResponse
import com.costa.matheus.filmesapi.repository.moviedetails.MovieDetailsRepository
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.view.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel (
    private val repository: MovieDetailsRepository) : BaseViewModel() {

    private val privateState = MutableStateFlow<RequestState<MovieDetailModel>>(RequestState.Success(null))
    val state: StateFlow<RequestState<MovieDetailModel>> get() = privateState

    private val privateCastState = MutableStateFlow<RequestState<CastResponse>>(RequestState.Success(null))
    val castState: StateFlow<RequestState<CastResponse>> get() = privateCastState


    fun getMovieDetail(movieId: Long) {
        jobs add launch {
            privateState.value = RequestState.Loading

            try {
                val response = repository.getMovie(movieId).await()
                privateState.value = RequestState.Success(response)
            }catch (t: Throwable) {
                privateState.value = RequestState.Error(t, false)
            }
        }
    }

    fun getCast(movieId: Long) {
        jobs add launch {
            privateCastState.value = RequestState.Loading

            try {
                val response = repository.getCast(movieId).await()
                privateCastState.value = RequestState.Success(response)
            }catch (t: Throwable) {
                privateCastState.value = RequestState.Error(t, false)
            }
        }
    }

    fun canShowVideo(videoResponse: VideoResponse) = videoResponse.results.isNotEmpty()

}