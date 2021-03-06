package com.costa.matheus.filmesapi.di

import com.costa.matheus.filmesapi.repository.MovieRepository
import com.costa.matheus.filmesapi.repository.MovieRepositoryImpl
import com.costa.matheus.filmesapi.repository.MoviesKeyedDataSource
import com.costa.matheus.filmesapi.repository.MoviesKeyedDataSourceFactory
import com.costa.matheus.filmesapi.view.moviedetails.MovieDetailsViewModel
import com.costa.matheus.filmesapi.view.movielist.MovieListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val app_module = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }

    factory { MoviesKeyedDataSource(get()) }
    single { MoviesKeyedDataSourceFactory() }
    single { MovieRepositoryImpl(get()) as MovieRepository }
}