package com.costa.matheus.filmesapi.repository.moviedetails

import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.dto.MovieDetailModel
import com.costa.matheus.filmesapi.model.response.CastResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface MovieDetailsRepository {
    suspend fun getMovie(movieId: Long): Deferred<MovieDetailModel>
    suspend fun getCast(movieId: Long): Deferred<CastResponse>
}

class MovieDetailsRepositoryImpl(private val dataSource: MovieDetailsDataSource) : MovieDetailsRepository {

    override suspend fun getMovie(movieId: Long) = withContext(Dispatchers.IO) {
        async {
            dataSource.getMovie(movieId, BuildConfig.MoviesAPIKey, "pt-BR", "videos")
        }
    }

    override suspend fun getCast(movieId: Long) = withContext(Dispatchers.IO) {
        async {
            dataSource.getCast(movieId, BuildConfig.MoviesAPIKey, "pt-BR")
        }
    }

}