package com.rama.necflix.network

interface DatasourceNetwork {
    suspend fun updateNowPlaying()
    suspend fun updateUpcomingMovies()
    suspend fun updateMoviesPopular()
    suspend fun updateMoviesTopRated()
    suspend fun updateSearchMulti(search: String, mode: String)
    suspend fun updateAiringTodayTvShow()
    suspend fun updateTvShowPopular()
    suspend fun updateTvShowTopRated()
    suspend fun updateDetailsOfMovie(id: Int)
}