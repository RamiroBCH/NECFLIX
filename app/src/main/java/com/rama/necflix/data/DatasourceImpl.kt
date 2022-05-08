package com.rama.necflix.data

import com.rama.necflix.domain.Datasource
import com.rama.necflix.domain.Webservice
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class DatasourceImpl @Inject constructor(val itemsDao: ItemsDao,val webservice: Webservice): Datasource {
    private val api_key = "f937e50a9ebe2079954b39dfed897360"

    override suspend fun insertAccountToRoom(account: Accounts) {
        itemsDao.insertAccount(account)
    }

    override suspend fun getAccountsFromDatabase(): Resource<List<Accounts>> {
        return Resource.Success(itemsDao.getAccountsFromDatabase())
    }

    override suspend fun getGuestSessionId(): String {
        itemsDao.insertAccountInvitado(AccountInvitado(0,""))
        var token: String = itemsDao.getAccountInvitado().token
        if(token == ""){
            val sessionId: String = webservice.getGuestSessionId(api_key).guest_session_id
            itemsDao.insertAccountInvitado(AccountInvitado(0,sessionId))
            token = itemsDao.getAccountInvitado().token
        }
        return token
    }

    override suspend fun getTokenNew(): String {
        return webservice.getTokenNew(api_key).request_token
    }

    override suspend fun createTokenActivated(getToken: Token): String {
        return webservice.createTokenActivated(api_key,getToken).request_token
    }

    override suspend fun createSessionId(tokenValidate: String): String {
        return webservice.createSessionId(api_key,tokenValidate).session_id
    }

    override suspend fun getGenre(): List<GenresDB> {
        var genre = webservice.getGenre(api_key)
        val list = genre.asGenreDB(genre.genres)
        for(i in list.indices){
            itemsDao.insertGenre(list[i])
        }
        val listDB = itemsDao.getGenre()
        return listDB
    }

}
