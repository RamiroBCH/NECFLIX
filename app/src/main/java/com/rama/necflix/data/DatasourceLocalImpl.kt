package com.rama.necflix.data

import com.rama.necflix.domain.DatasourceLocal
import com.rama.necflix.domain.Webservice
import com.rama.necflix.utils.*
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class DatasourceLocalImpl @Inject constructor(private val itemsDao: ItemsDao, private val webservice: Webservice) :
    DatasourceLocal {
    private val apikey = "f937e50a9ebe2079954b39dfed897360"
    private val language = "es"
    private val append = "videos,images"

    override suspend fun insertAccountToRoom(account: Accounts) {
        itemsDao.insertAccount(account)
    }

    override suspend fun getAccountsFromDatabase(): Resource<List<Accounts>> {
        return Resource.Success(itemsDao.getAccountsFromDatabase())
    }

    override suspend fun getGuestSessionId(): Resource<String> {
        var token: AccountInvitado? = itemsDao.getAccountInvitado(0)
        return if (token == null) {
            val sessionId: String = webservice.getGuestSessionId(apikey).guest_session_id
            itemsDao.insertAccountInvitado(AccountInvitado(0, sessionId))
            token = itemsDao.getAccountInvitado(0)!!
            Resource.Success(token.token)
        } else {
            Resource.Success(token.token)
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
        val nowPlayingListDB = itemsDao.getMoviesFromDB(type)
        return Resource.Success(nowPlayingListDB)
    }

    override suspend fun getUpcomingMovies(): Resource<List<resultsDB>> {
        val type = "Upcoming"
        val upcomingMoviesListDB = itemsDao.getMoviesFromDB(type)
        return Resource.Success(upcomingMoviesListDB)
    }

    override suspend fun getMoviesPopular(): Resource<List<resultsDB>> {
        val type = "Popular"
        val moviesPopularListDB = itemsDao.getMoviesFromDB(type)
        return Resource.Success(moviesPopularListDB)
    }

    override suspend fun getMoviesTopRated(): Resource<List<resultsDB>> {
        val type = "Top Rated"
        val moviesTopRatedListDB = itemsDao.getMoviesFromDB(type)
        return Resource.Success(moviesTopRatedListDB)
    }

    override suspend fun getSearchMulti(search: String, mode: String): Resource<List<resultsDB>> {
        return if(mode == "movie"){
            val moviesSearchMultiListDB = itemsDao.getMoviesFromDB(search)
            Resource.Success(moviesSearchMultiListDB)
        }else{
            val tvShowsSearchMultiListDB = itemsDao.getTvShowsFromDB(search)
            Resource.Success(asNormalResultDB(tvShowsSearchMultiListDB))
        }
    }

    override suspend fun getAiringTodayTvShow(): Resource<List<resultsDB>> {
        val type = "AiringToday"
        val tvShowsAiringTodayDB = itemsDao.getTvShowsFromDB(type)
        return Resource.Success(asNormalResultDB(tvShowsAiringTodayDB))
    }

    override suspend fun getTvShowPopular(): Resource<List<resultsDB>> {
        val type = "Tv Popular"
        val tvShowsPopularDB = itemsDao.getTvShowsFromDB(type)
        return Resource.Success(asNormalResultDB(tvShowsPopularDB))
    }

    override suspend fun getTvShowTopRated(): Resource<List<resultsDB>> {
        val type = "Tv Top Rated"
        val tvShowsTopRatedDB = itemsDao.getTvShowsFromDB(type)
        return Resource.Success(asNormalResultDB(tvShowsTopRatedDB))
    }

    override suspend fun getDetailsOfMovie(id: Int,title: String): Resource<normalDetailsOfMovie> {
        val genero = itemsDao.getDetailsGenres(id)
        val posterUr = itemsDao.getDetailsPoster(title)
        val videosUr = itemsDao.getDetailsVideos(title)
        val detailsOfRes = itemsDao.getMoviesDetailsDB(id)
        val detailsComplete = convertComplete(detailsOfRes, genero, posterUr, videosUr)
        return Resource.Success(detailsComplete)
    }

    override suspend fun getMovieInformationById(id: Int): resultsDB {
        return itemsDao.getMovieInformationById(id)
    }

}



