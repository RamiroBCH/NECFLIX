package com.rama.necflix.domain

import com.rama.necflix.data.Accounts
import com.rama.necflix.data.GenresDB
import com.rama.necflix.data.Token
import com.rama.necflix.data.genre
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(private val datasource: Datasource): Repo {
    override suspend fun insertAccountToRoom(account: Accounts) {
        datasource.insertAccountToRoom(account)
    }

    override suspend fun getAccountsFromDatabase(): Resource<List<Accounts>> {
        return datasource.getAccountsFromDatabase()
    }

    override suspend fun getGuestSessionId(): String {
        return datasource.getGuestSessionId()
    }

    override suspend fun getTokenNew(): String {
        return datasource.getTokenNew()
    }

    override suspend fun createTokenActivated(getToken: Token): String {
        return datasource.createTokenActivated(getToken)
    }

    override suspend fun createSessionId(tokenValidate: String): String {
        return datasource.createSessionId(tokenValidate)
    }

    override suspend fun getGenre(): List<GenresDB> {
        return datasource.getGenre()
    }

}