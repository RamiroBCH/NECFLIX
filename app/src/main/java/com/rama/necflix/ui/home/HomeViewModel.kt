package com.rama.necflix.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rama.necflix.data.GenreX
import com.rama.necflix.data.GenresDB
import com.rama.necflix.data.genre
import com.rama.necflix.domain.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: Repo): ViewModel() {
    fun getGenre(): List<GenresDB> {
        var genre = listOf<GenresDB>()
        viewModelScope.launch {
            genre = repo.getGenre()
        }

        return genre
    }

}