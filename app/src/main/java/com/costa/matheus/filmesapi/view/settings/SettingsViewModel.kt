package com.costa.matheus.filmesapi.view.settings

import com.costa.matheus.filmesapi.model.dto.AccountModel
import com.costa.matheus.filmesapi.model.dto.SessionModel
import com.costa.matheus.filmesapi.repository.settings.LocalDataRepository
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.utils.Constants
import com.costa.matheus.filmesapi.view.base.BaseViewModel
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception

class SettingsViewModel(
    private val repository: LocalDataRepository) : BaseViewModel() {

    fun getSession(): SessionModel? {
        return repository.getSession()
    }

    fun getAccount(): AccountModel? {
        return repository.getAccount()
    }

    fun isAutoPlayEnabled(): Boolean {
        return repository.isAutoPlayEnabled()
    }

    fun setAutoPlaySetting(isEnabled: Boolean) {
        repository.setAutoPlaySetting(isEnabled)
    }

    fun isAudioEnabled(): Boolean {
        return repository.isAudioEnabled()
    }

    fun setAudioSetting(isEnabled: Boolean) {
        repository.setAudioSetting(isEnabled)
    }

    fun isHqImageEnabled(): Boolean {
        return repository.isHqImageEnabled()
    }

    fun setHqImageSetting(isEnabled: Boolean) {
        repository.setHqImageSetting(isEnabled)
    }

}