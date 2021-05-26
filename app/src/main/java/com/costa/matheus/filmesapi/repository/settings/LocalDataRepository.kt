package com.costa.matheus.filmesapi.repository.settings

import com.costa.matheus.filmesapi.model.dto.AccountModel
import com.costa.matheus.filmesapi.model.dto.SessionModel
import com.costa.matheus.filmesapi.utils.Constants
import com.orhanobut.hawk.Hawk
import java.lang.Exception

interface LocalDataRepository {
    fun getSession(): SessionModel
    fun getAccount(): AccountModel
}

class LocalDataRepositoryImpl : LocalDataRepository {

    override fun getSession(): SessionModel {
        if(Hawk.contains(Constants.sessionIdKey)){
            return Hawk.get(Constants.sessionIdKey)
        }
        throw Exception()
    }

    override fun getAccount(): AccountModel {
        if(Hawk.contains(Constants.accountKey)){
            return Hawk.get(Constants.accountKey)
        }
        throw Exception()
    }

}