package com.rama.necflix.data

import com.rama.necflix.domain.DatasourceLocal
import com.rama.necflix.domain.Webservice
import com.rama.necflix.utils.*
import com.rama.necflix.vo.*
import javax.inject.Inject

class DatasourceLocalImpl @Inject constructor(private val itemsDao: ItemsDao, private val webservice: Webservice) :
    DatasourceLocal {

    override suspend fun insertAccountToRoom(account: Accounts) {
        itemsDao.insertAccount(account)
    }

    override suspend fun getAccountsFromDatabase(): Resource<List<Accounts>> {
        return Resource.Success(itemsDao.getAccountsFromDatabase())
    }

    override suspend fun getGuestSessionId(): Resource<AccountInvitado> {
        var token: AccountInvitado? = itemsDao.getAccountInvitado(0)
        return if (token == null) {
            val sessionId: String = webservice.getGuestSessionId(apikey).guest_session_id
            itemsDao.insertAccountInvitado(AccountInvitado(0, invitado, sessionId))
            token = itemsDao.getAccountInvitado(0)!!
            Resource.Success(token)
        } else {
            Resource.Success(token)
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
        val listDB = itemsDao.getGenre()
        return Resource.Success(listDB)
    }

    override suspend fun getNowPlaying(): Resource<List<resultsDB>> {
        val nowPlayingListDB = itemsDao.getMoviesFromDB(nowplaying)
        return Resource.Success(nowPlayingListDB)
    }

    override suspend fun getUpcomingMovies(): Resource<List<resultsDB>> {
        val upcomingMoviesListDB = itemsDao.getMoviesFromDB(Upcoming)
        return Resource.Success(upcomingMoviesListDB)
    }

    override suspend fun getMoviesPopular(): Resource<List<resultsDB>> {
        val moviesPopularListDB = itemsDao.getMoviesFromDB(Popular)
        return Resource.Success(moviesPopularListDB)
    }

    override suspend fun getMoviesTopRated(): Resource<List<resultsDB>> {
        val moviesTopRatedListDB = itemsDao.getMoviesFromDB(TopRated)
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
        val tvShowsAiringTodayDB = itemsDao.getTvShowsFromDB(AiringToday)
        return Resource.Success(asNormalResultDB(tvShowsAiringTodayDB))
    }

    override suspend fun getTvShowPopular(): Resource<List<resultsDB>> {
        val tvShowsPopularDB = itemsDao.getTvShowsFromDB(TvPopular)
        return Resource.Success(asNormalResultDB(tvShowsPopularDB))
    }

    override suspend fun getTvShowTopRated(): Resource<List<resultsDB>> {
        val tvShowsTopRatedDB = itemsDao.getTvShowsFromDB(TvTopRated)
        return Resource.Success(asNormalResultDB(tvShowsTopRatedDB))
    }

    override suspend fun getDetailsOfMovie(id: Int,title: String): Resource<normalDetailsOfMovie> {
        val genero = itemsDao.getDetailsGenres(id)
        val posterUr = itemsDao.getDetailsPoster(title)
        val videosUr = itemsDao.getDetailsVideos(title)
        val detailsOfRes = itemsDao.getMoviesDetailsDB(title)
        val detailsComplete = convertComplete(detailsOfRes, genero, posterUr, videosUr)
        return Resource.Success(detailsComplete)
    }

    override suspend fun getMovieInformationById(id: Int): resultsDB {
        return itemsDao.getMovieInformationById(id)
    }

    override suspend fun getActiveAccountsFromDatabase(): Resource<Accounts> {
        return Resource.Success(itemsDao.getActiveAcc(true))
    }

    /*override suspend fun updateAccounts() {
        itemsDao.
    }*/

}



