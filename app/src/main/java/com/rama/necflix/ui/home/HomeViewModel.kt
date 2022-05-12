package com.rama.necflix.ui.home

import androidx.core.view.get
import androidx.lifecycle.*
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
                for (i in 0 until count) {
                    binding.recyclerNowplaying.smoothScrollToPosition(i)
                    delay(4000)
                }
                for(i in count downTo 0){
                    binding.recyclerNowplaying.smoothScrollToPosition(i)
                    delay(4000)
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

    private var type = MutableLiveData<String?>()
    fun setType(typePass: String?){
        type.value = typePass
    }
    val getMoviesFromDB = type.distinctUntilChanged().switchMap { type ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                when (type) {
                    "Upcoming" -> emit(repo.getUpcomingMovies())
                    "Popular" -> emit(repo.getMoviesPopular())
                    "Top Rated" -> emit(repo.getMoviesTopRated())
                }

            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

}