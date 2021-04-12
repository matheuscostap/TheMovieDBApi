package com.costa.matheus.filmesapi.di

import com.costa.matheus.filmesapi.repository.catalogue.CatalogueDataSource
import com.costa.matheus.filmesapi.repository.celebrities.CelebritiesDataSource
import com.costa.matheus.filmesapi.repository.moviedetails.MovieDetailsDataSource
import com.costa.matheus.filmesapi.repository.searchresults.SearchResultsDataSource
import com.costa.matheus.filmesapi.repository.searchresults.SearchResultsRepository
import com.costa.matheus.filmesapi.repository.trending.TrendingDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val API_URL = "https://api.themoviedb.org/"

val retrofit_module = module {
    single { createOkHttpClient() }
    single { createWebService<TrendingDataSource>(get(), API_URL) }
    single { createWebService<CatalogueDataSource>(get(), API_URL) }
    single { createWebService<CelebritiesDataSource>(get(), API_URL) }
    single { createWebService<MovieDetailsDataSource>(get(), API_URL) }
    single { createWebService<SearchResultsDataSource>(get(), API_URL) }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
    return retrofit.create(T::class.java)
}