package com.rama.necflix.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rama.necflix.domain.Repo
import com.rama.necflix.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DetailsVM @Inject constructor(private val repo: Repo) : ViewModel() {
    private val idVm = MutableLiveData<Int>()
    fun getDetails(id: Int) {
        idVm.value = id
    }
    val details = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(repo.getDetailsOfMovie(idVm.value!!))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}