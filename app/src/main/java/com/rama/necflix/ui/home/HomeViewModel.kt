package com.rama.necflix.ui.home

import androidx.lifecycle.*
import com.rama.necflix.data.GenreX
import com.rama.necflix.data.GenresDB
import com.rama.necflix.data.genre
import com.rama.necflix.domain.Repo
import com.rama.necflix.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: Repo): ViewModel() {

    val getGenre = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try{
            emit(repo.getGenre())
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }

}