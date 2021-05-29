package com.costa.matheus.filmesapi.di

import com.costa.matheus.filmesapi.repository.account.AccountRepository
import com.costa.matheus.filmesapi.repository.account.AccountRepositoryImpl
import com.costa.matheus.filmesapi.repository.catalogue.CatalogueRepository
import com.costa.matheus.filmesapi.repository.catalogue.CatalogueRepositoryImpl
import com.costa.matheus.filmesapi.repository.celebrities.CelebritiesRepository
import com.costa.matheus.filmesapi.repository.celebrities.CelebritiesRepositoryImpl
import com.costa.matheus.filmesapi.repository.login.LoginRepository
import com.costa.matheus.filmesapi.repository.login.LoginRepositoryImpl
import com.costa.matheus.filmesapi.repository.moviedetails.MovieDetailsRepository
import com.costa.matheus.filmesapi.repository.moviedetails.MovieDetailsRepositoryImpl
import com.costa.matheus.filmesapi.repository.searchresults.SearchResultsRepository
import com.costa.matheus.filmesapi.repository.searchresults.SearchResultsRepositoryImpl
import com.costa.matheus.filmesapi.repository.settings.LocalDataRepository
import com.costa.matheus.filmesapi.repository.settings.LocalDataRepositoryImpl
import com.costa.matheus.filmesapi.repository.trending.TrendingRepository
import com.costa.matheus.filmesapi.repository.trending.TrendingRepositoryImpl
import com.costa.matheus.filmesapi.utils.ImagePath
import com.costa.matheus.filmesapi.view.catalogue.CatalogueViewModel
import com.costa.matheus.filmesapi.view.celebrities.CelebritiesViewModel
import com.costa.matheus.filmesapi.view.login.LoginViewModel
import com.costa.matheus.filmesapi.view.moviedetails.MovieDetailsViewModel
import com.costa.matheus.filmesapi.view.searchresults.SearchResultsViewModel
import com.costa.matheus.filmesapi.view.settings.SettingsViewModel
import com.costa.matheus.filmesapi.view.trending.TrendingViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val app_module = module {
    viewModel { TrendingViewModel(get()) }
    viewModel { CatalogueViewModel(get()) }
    viewModel { CelebritiesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { SearchResultsViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }

    single { TrendingRepositoryImpl(get()) as TrendingRepository }
    single { CatalogueRepositoryImpl(get()) as CatalogueRepository}
    single { CelebritiesRepositoryImpl(get()) as CelebritiesRepository}
    single { MovieDetailsRepositoryImpl(get()) as MovieDetailsRepository }
    single { SearchResultsRepositoryImpl(get()) as SearchResultsRepository}
    single { LoginRepositoryImpl(get()) as LoginRepository }
    single { AccountRepositoryImpl(get()) as AccountRepository }
    single { LocalDataRepositoryImpl() as LocalDataRepository }
}