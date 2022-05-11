package com.rama.necflix.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rama.necflix.domain.Repo
import com.rama.necflix.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: Repo): ViewModel() {

    val getNowPlaying = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try{
            emit(repo.getNowPlaying())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    val getGenre = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try{
            emit(repo.getGenre())
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }

}