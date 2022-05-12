package com.rama.necflix.data

import com.rama.necflix.domain.Datasource
import com.rama.necflix.domain.Webservice
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class DatasourceImpl @Inject constructor(val itemsDao: ItemsDao, val webservice: Webservice) :
    Datasource {
    val apikey = "f937e50a9ebe2079954b39dfed897360"
    val language = "es-ES"

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

    override suspend fun getNowPlaying(): Resource<List<resultsDB>> {
        val type = "nowplaying"
        val nowPlayingList = webservice.getNowPlaying(apikey, language).results
        val convert = mapConvert(nowPlayingList,type)
        return Resource.Success(convert)
    }

    override suspend fun getUpcomingMovies(): Resource<List<resultsDB>> {
        val type = "Upcoming"
        val upcomingMoviesList = webservice.getUpcomingMovies(apikey, language).results
        val convert = mapConvert(upcomingMoviesList,type)
        insertDB(convert)
        val upcomingMoviesListDB = itemsDao.getMoviesFromDB(type)
        return Resource.Success(upcomingMoviesListDB)
    }

    override suspend fun getMoviesPopular(): Resource<List<resultsDB>> {
        val type = "Popular"
        val moviesPopularList = webservice.getMoviesPopular(apikey, language).results
        val listConvert = mapConvert(moviesPopularList,type)
        insertDB(listConvert)
        val moviesPopularListDB = itemsDao.getMoviesFromDB(type)
        return Resource.Success(moviesPopularListDB)
    }

    override suspend fun getMoviesTopRated(): Resource<List<resultsDB>> {
        val type = "Top Rated"
        val moviesTopRatedList = webservice.getTopRated(apikey, language).results
        val listConvert = mapConvert(moviesTopRatedList, type)
        insertDB(listConvert)
        val moviesTopRatedListDB = itemsDao.getMoviesFromDB(type)
        return Resource.Success(moviesTopRatedListDB)
    }
    //******************************************************************************************
    //funcion para insertar en room
    suspend fun insertDB(convert: List<resultsDB>) {
        for (i in convert.indices) {
            itemsDao.insertUpcomingMovie(
                resultsDB(
                    convert[i].id,
                    convert[i].type,
                    convert[i].adult,
                    convert[i].backdrop_path,
                    convert[i].genre_ids,
                    convert[i].original_language,
                    convert[i].original_title,
                    convert[i].overview,
                    convert[i].popularity,
                    convert[i].poster_path,
                    convert[i].release_date,
                    convert[i].title,
                    convert[i].video,
                    convert[i].vote_average,
                    convert[i].vote_count
                )
            )
        }
    }
}

fun mapConvert(results: List<Result>, type: String): List<resultsDB> {
    return results.map {
        resultsDB(
            it.id,
            type,
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
}

