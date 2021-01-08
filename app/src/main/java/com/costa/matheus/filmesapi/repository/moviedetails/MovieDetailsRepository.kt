package com.costa.matheus.filmesapi.repository.moviedetails

import com.costa.matheus.filmesapi.BuildConfig
import com.costa.matheus.filmesapi.model.dto.MovieDetailModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface MovieDetailsRepository {
    suspend fun getMovie(movieId: Long) : Deferred<MovieDetailModel>
}

class MovieDetailsRepositoryImpl(private val dataSource: MovieDetailsDataSource) : MovieDetailsRepository {

    override suspend fun getMovie(movieId: Long) = withContext(Dispatchers.IO) {
        async {
            dataSource.getMovie(movieId, BuildConfig.MoviesAPIKey, "pt-BR", "videos")
        }
    }

}