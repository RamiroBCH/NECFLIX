package com.rama.necflix.domain

import com.rama.necflix.data.*
import com.rama.necflix.vo.Resource


interface Datasource {
    suspend fun insertAccountToRoom(account: Accounts)
    suspend fun getAccountsFromDatabase(): Resource<List<Accounts>>
    suspend fun getGuestSessionId(): Resource<String>
    suspend fun getTokenNew(): Resource<String>
    suspend fun createTokenActivated(getToken: Token): Resource<String>
    suspend fun createSessionId(tokenValidate: String): Resource<String>
    suspend fun getGenre(): Resource<List<GenresDB>>
    suspend fun getNowPlaying(): Resource<List<resultsDB>>
    suspend fun getUpcomingMovies(): Resource<List<resultsDB>>
    suspend fun getMoviesPopular(): Resource<List<resultsDB>>
    suspend fun getMoviesTopRated(): Resource<List<resultsDB>>
}