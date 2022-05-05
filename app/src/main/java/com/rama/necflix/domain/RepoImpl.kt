package com.rama.necflix.domain

import com.rama.necflix.data.Accounts
import com.rama.necflix.data.DrawableResourceUrl
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(private val datasource: Datasource): Repo {
    override suspend fun insertDrawableName(list: List<DrawableResourceUrl>) {
        datasource.insertDrawableName(list)
    }

    override suspend fun insertAccountToRoom(account: Accounts) {
        datasource.insertAccountToRoom(account)
    }

    override suspend fun getDrawableSrc(): List<DrawableResourceUrl> {
        return datasource.getDrawableSrc()
    }

    override suspend fun getAccountsFromDatabase(): Resource<List<Accounts>> {
        return datasource.getAccountsFromDatabase()
    }

}