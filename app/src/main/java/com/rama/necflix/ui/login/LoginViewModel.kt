package com.rama.necflix.ui.login

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rama.necflix.data.Accounts
import com.rama.necflix.data.DrawableResourceName
import com.rama.necflix.domain.Repo
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: Repo) : ViewModel() {
    fun getAccountsFromDatabase() {
        //crear una funcion que llame al repo
    }
    /*val getApod = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.getItemApod())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }*/
    fun insertDrawableName(list: List<DrawableResourceName>){
        viewModelScope.launch {
            repo.insertDrawableName(list)
        }
    }

    fun insertAccountToRoom(account: Accounts) {
        viewModelScope.launch {
            repo.insertAccountToRoom(account)
        }
    }

}