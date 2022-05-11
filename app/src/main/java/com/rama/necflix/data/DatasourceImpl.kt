package com.rama.necflix.data

import com.rama.necflix.domain.Datasource
import com.rama.necflix.domain.Webservice
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class DatasourceImpl @Inject constructor(val itemsDao: ItemsDao, val webservice: Webservice) :
    Datasource {
    val apikey = "f937e50a9ebe2079954b39dfed897360"

    override suspend fun insertAccountToRoom(account: Accounts) {
        itemsDao.insertAccount(account)
    }

    override suspend fun getAccountsFromDatabase(): Resource<List<Accounts>> {
        return Resource.Success(itemsDao.getAccountsFromDatabase())
    }

    override suspend fun getGuestSessionId(): Resource<String> {
        var token: AccountInvitado? = itemsDao.getAccountInvitado(0)
        if (token == null) {
            val sessionId: String = webservice.getGuestSessionId(apikey).guest_session_id
            itemsDao.insertAccountInvitado(AccountInvitado(0, sessionId))
            token = itemsDao.getAccountInvitado(0)!!
            return Resource.Success(token.token)
        } else {
            return Resource.Success(token.token)
        }
    }

    override suspend fun getTokenNew(): Resource<String> {
        return Resource.Success(webservice.getTokenNew(apikey).request_token)
    }

    override suspend fun createTokenActivated(getToken: Token): Resource<String> {
        return Resource.Success(webservice.createTokenActivated(apikey, getToken).request_token)
    }

    override suspend fun createSessionId(tokenValidate: String): Resource<String> {
        return Resource.Success(webservice.createSessionId(apikey, tokenValidate).session_id)
    }

    override suspend fun getGenre(): Resource<List<GenresDB>> {
        val list = webservice.getGenre(apikey)
        for (i in list.genres.indices) {
            itemsDao.insertGenre(GenresDB(list.genres[i].id, list.genres[i].name))
        }
        val listDB = itemsDao.getGenre()
        return Resource.Success(listDB)
    }

    override suspend fun getNowPlaying(): Resource<List<NowPlayingDB>> {
        val language = "es-ES"
        val nowPlayingList = webservice.getNowPlaying(apikey, language).results.map {
            NowPlayingDB(
                it.id,
                it.adult,
                it.backdrop_path,
                it.genre_ids[0],
                it.original_language,
                it.original_title,
                it.overview,
                it.popularity,
                it.poster_path,
                it.release_date,
                it.title,
                it.video,
                it.vote_average,
                it.vote_count
            )
        }
        for (i in nowPlayingList.indices){
            itemsDao.insertListNowPlaying(nowPlayingList[i])
        }
        val listNowPlayingDB = itemsDao.getNowPlaying()

        return Resource.Success(listNowPlayingDB)
    }

}
