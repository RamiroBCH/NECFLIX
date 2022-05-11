package com.rama.necflix.ui.home

import androidx.core.view.get
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rama.necflix.databinding.FragmentHomeBinding
import com.rama.necflix.domain.Repo
import com.rama.necflix.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    fun smoothScrolling(count: Int, binding: FragmentHomeBinding) {
        viewModelScope.launch {
            while(true){
                for (i in 0 until 10) {
                    binding.recyclerNowplaying.smoothScrollToPosition(i)
                    delay(2000)
                }
                for(i in 0 until 1){
                    binding.recyclerNowplaying.smoothScrollToPosition(0)
                }
            }
        }
    }

    val getNowPlaying = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.getNowPlaying())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
    val getGenre = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.getGenre())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}