package com.rama.necflix.domain

import com.rama.necflix.data.Accounts
import com.rama.necflix.data.GenresDB
import com.rama.necflix.data.Token
import com.rama.necflix.data.genre
import com.rama.necflix.vo.Resource


interface Repo {
    suspend fun insertAccountToRoom(account: Accounts)
    suspend fun getAccountsFromDatabase(): Resource<List<Accounts>>
    suspend fun getGuestSessionId(): String
    suspend fun getTokenNew(): String
    suspend fun createTokenActivated(getToken: Token): String
    suspend fun createSessionId(tokenValidate: String): String
    suspend fun getGenre(): List<GenresDB>
}