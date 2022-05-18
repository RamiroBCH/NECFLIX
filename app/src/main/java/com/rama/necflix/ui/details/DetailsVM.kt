package com.rama.necflix.ui.details

import androidx.lifecycle.*
import com.rama.necflix.data.resultsDB
import com.rama.necflix.domain.Repo
import com.rama.necflix.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsVM @Inject constructor(private val repo: Repo) : ViewModel() {
    private val idVm = MutableLiveData<Int>()
    private var title: String? = ""

    fun getDetails(id: Int) {
        idVm.value = id
        viewModelScope.launch {
            title = try {
                repo.getMovieInformationById(id).title
            } catch (e: Exception) {
                ""
            }
        }
    }

    val details = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.getDetailsOfMovie(idVm.value!!, title.toString()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}