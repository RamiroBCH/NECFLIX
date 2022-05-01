package com.rama.necflix.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class LoginViewModel : ViewModel() {
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

}