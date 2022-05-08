package com.rama.necflix.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rama.necflix.data.Accounts
import com.rama.necflix.data.Token
import com.rama.necflix.domain.Repo
import com.rama.necflix.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: Repo) : ViewModel() {
    val accounts = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(repo.getAccountsFromDatabase())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    /*val getApod = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.getItemApod())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }*/

    fun insertAccountToRoom(account: Accounts) {
        viewModelScope.launch {
            repo.insertAccountToRoom(account)
        }
    }

    fun getGuestSessionId(): String {
        var sessionId: String = ""
        viewModelScope.launch {
            sessionId = repo.getGuestSessionId()
        }
        return sessionId
    }

    fun createSessionId(tokenValidate: String): String {
        var sessionId: String = ""
        viewModelScope.launch {
            repo.createSessionId(tokenValidate)
        }
        return sessionId
    }

    fun getTokenNew(): String {
        var tokenNew: String = ""
        viewModelScope.launch {
            tokenNew = repo.getTokenNew()
        }
        return tokenNew
    }

    fun createTokenActivated(getToken: Token): String {
        var token: String = ""
        viewModelScope.launch {
            token = repo.createTokenActivated(getToken)
        }
        return token
    }


}