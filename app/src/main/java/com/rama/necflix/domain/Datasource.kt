package com.rama.necflix.domain

import com.rama.necflix.data.Accounts
import com.rama.necflix.data.DrawableResourceUrl
import com.rama.necflix.vo.Resource


interface Datasource {
    suspend fun insertDrawableName(list: List<DrawableResourceUrl>)
    suspend fun insertAccountToRoom(account: Accounts)
    suspend fun getDrawableSrc(): List<DrawableResourceUrl>
    suspend fun getAccountsFromDatabase(): Resource<List<Accounts>>
}