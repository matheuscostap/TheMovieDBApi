package com.costa.matheus.filmesapi.repository.settings

import com.costa.matheus.filmesapi.model.dto.AccountModel
import com.costa.matheus.filmesapi.model.dto.SessionModel
import com.costa.matheus.filmesapi.utils.Constants
import com.orhanobut.hawk.Hawk
import java.lang.Exception

interface LocalDataRepository {
    fun getSession(): SessionModel?
    fun getAccount(): AccountModel?
    fun isAutoPlayEnabled(): Boolean
    fun setAutoPlaySetting(isEnabled: Boolean)
    fun isAudioEnabled(): Boolean
    fun setAudioSetting(isEnabled: Boolean)
    fun isHqImageEnabled(): Boolean
    fun setHqImageSetting(isEnabled: Boolean)
}

class LocalDataRepositoryImpl : LocalDataRepository {

    override fun getSession(): SessionModel? {
        if(Hawk.contains(Constants.sessionIdKey)){
            return Hawk.get(Constants.sessionIdKey)
        }else{
            return null
        }
    }

    override fun getAccount(): AccountModel? {
        if(Hawk.contains(Constants.accountKey)){
            return Hawk.get(Constants.accountKey)
        }else{
            return null
        }
    }

    override fun isAutoPlayEnabled(): Boolean {
        if(Hawk.contains(Constants.autoPlayKey)){
            return Hawk.get(Constants.autoPlayKey)
        }else{
            Hawk.put(Constants.autoPlayKey, true)
            return Hawk.get(Constants.autoPlayKey)
        }
    }

    override fun setAutoPlaySetting(isEnabled: Boolean) {
        Hawk.put(Constants.autoPlayKey, isEnabled)
    }

    override fun isAudioEnabled(): Boolean {
        if(Hawk.contains(Constants.audioKey)){
            return Hawk.get(Constants.audioKey)
        }else{
            Hawk.put(Constants.audioKey, true)
            return Hawk.get(Constants.audioKey)
        }
    }

    override fun setAudioSetting(isEnabled: Boolean) {
        Hawk.put(Constants.audioKey, isEnabled)
    }

    override fun isHqImageEnabled(): Boolean {
        if(Hawk.contains(Constants.hqImageKey)){
            return Hawk.get(Constants.hqImageKey)
        }else{
            Hawk.put(Constants.hqImageKey, true)
            return Hawk.get(Constants.hqImageKey)
        }
    }

    override fun setHqImageSetting(isEnabled: Boolean) {
        Hawk.put(Constants.hqImageKey, isEnabled)
    }

}