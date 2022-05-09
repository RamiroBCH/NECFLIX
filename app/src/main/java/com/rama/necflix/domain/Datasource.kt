package com.rama.necflix.domain

import com.rama.necflix.data.Accounts
import com.rama.necflix.data.GenresDB
import com.rama.necflix.data.Token
import com.rama.necflix.data.genre
import com.rama.necflix.vo.Resource


interface Datasource {
    suspend fun insertAccountToRoom(account: Accounts)
    suspend fun getAccountsFromDatabase(): Resource<List<Accounts>>
    suspend fun getGuestSessionId(): Resource<String>
    suspend fun getTokenNew(): Resource<String>
    suspend fun createTokenActivated(getToken: Token): Resource<String>
    suspend fun createSessionId(tokenValidate: String): Resource<String>
    suspend fun getGenre(): Resource<List<GenresDB>>
}