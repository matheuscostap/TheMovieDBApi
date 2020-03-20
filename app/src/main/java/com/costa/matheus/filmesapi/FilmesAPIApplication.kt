package com.costa.matheus.filmesapi

import android.app.Application
import com.costa.matheus.filmesapi.di.app_module
import com.costa.matheus.filmesapi.di.retrofit_module
import org.koin.android.ext.android.startKoin

class FilmesAPIApplication: Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(app_module, retrofit_module))
    }
}