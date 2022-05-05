package com.rama.necflix.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rama.necflix.data.Accounts
import com.rama.necflix.data.DrawableResourceUrl
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
    fun insertDrawableName(list: List<DrawableResourceUrl>){
        viewModelScope.launch {
            repo.insertDrawableName(list)
        }
    }

    fun insertAccountToRoom(account: Accounts) {
        viewModelScope.launch {
            repo.insertAccountToRoom(account)
        }
    }

    fun getDrawableSrc(): List<DrawableResourceUrl> {
        var url: List<DrawableResourceUrl> = emptyList()
        viewModelScope.launch {
            url = repo.getDrawableSrc()
        }
        return url
    }


}