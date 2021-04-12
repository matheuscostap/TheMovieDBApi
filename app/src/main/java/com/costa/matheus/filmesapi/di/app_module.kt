package com.costa.matheus.filmesapi.di

import com.costa.matheus.filmesapi.repository.catalogue.CatalogueRepository
import com.costa.matheus.filmesapi.repository.catalogue.CatalogueRepositoryImpl
import com.costa.matheus.filmesapi.repository.celebrities.CelebritiesRepository
import com.costa.matheus.filmesapi.repository.celebrities.CelebritiesRepositoryImpl
import com.costa.matheus.filmesapi.repository.moviedetails.MovieDetailsRepository
import com.costa.matheus.filmesapi.repository.moviedetails.MovieDetailsRepositoryImpl
import com.costa.matheus.filmesapi.repository.searchresults.SearchResultsRepository
import com.costa.matheus.filmesapi.repository.searchresults.SearchResultsRepositoryImpl
import com.costa.matheus.filmesapi.repository.trending.TrendingDataSource
import com.costa.matheus.filmesapi.repository.trending.TrendingRepository
import com.costa.matheus.filmesapi.repository.trending.TrendingRepositoryImpl
import com.costa.matheus.filmesapi.view.catalogue.CatalogueViewModel
import com.costa.matheus.filmesapi.view.celebrities.CelebritiesViewModel
import com.costa.matheus.filmesapi.view.moviedetails.MovieDetailsViewModel
import com.costa.matheus.filmesapi.view.searchresults.SearchResultsViewModel
import com.costa.matheus.filmesapi.view.trending.TrendingViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val app_module = module {
    viewModel { TrendingViewModel(get()) }
    viewModel { CatalogueViewModel(get()) }
    viewModel { CelebritiesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { SearchResultsViewModel(get()) }

    single { TrendingRepositoryImpl(get()) as TrendingRepository }
    single { CatalogueRepositoryImpl(get()) as CatalogueRepository}
    single { CelebritiesRepositoryImpl(get()) as CelebritiesRepository}
    single { MovieDetailsRepositoryImpl(get()) as MovieDetailsRepository }
    single { SearchResultsRepositoryImpl(get()) as SearchResultsRepository}
}