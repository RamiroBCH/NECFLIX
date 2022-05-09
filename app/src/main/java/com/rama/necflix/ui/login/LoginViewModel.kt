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

    fun insertAccountToRoom(account: Accounts) {
        viewModelScope.launch {
            repo.insertAccountToRoom(account)
        }
    }

    val getGuestSessionId = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.getGuestSessionId())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    val getTokenNew = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.getTokenNew())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    var getToken: Token = Token("","","")
    val createTokenActivated = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.createTokenActivated(getToken))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    var tokenValidate: String = ""
    val createSessionId = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.createSessionId(tokenValidate))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}