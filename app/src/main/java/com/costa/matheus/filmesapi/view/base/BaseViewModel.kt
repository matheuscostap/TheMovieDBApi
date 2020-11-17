package com.costa.matheus.filmesapi.view.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlin.coroutines.CoroutineContext


open class BaseViewModel: ViewModel(), CoroutineScope {

    //Todas as coroutines desse contexto serão executadas na Main Thread
    override val coroutineContext: CoroutineContext = MainScope().coroutineContext

    protected val jobs = ArrayList<Job>()

    infix fun ArrayList<Job>.add(job: Job) { this.add(job) }

    //Cancela todos os jobs quando a viewmodel chegar no fim do seu ciclo de vida
    override fun onCleared() {
        super.onCleared()
        jobs.forEach {if (!it.isCancelled) it.cancel()}
    }

}