package com.rama.necflix.domain

import com.rama.necflix.data.*
import com.rama.necflix.network.DatasourceNetwork
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val datasourceLocal: DatasourceLocal,
    private val datasourceNetwork: DatasourceNetwork):
    Repo {
    override suspend fun updateAll() {
        datasourceNetwork.updateNowPlaying()
        datasourceNetwork.updateUpcomingMovies()
        datasourceNetwork.updateMoviesPopular()
        datasourceNetwork.updateMoviesTopRated()
        datasourceNetwork.updateAiringTodayTvShow()
        datasourceNetwork.updateTvShowPopular()
        datasourceNetwork.updateTvShowTopRated()
    }

    override suspend fun insertAccountToRoom(account: Accounts) {
        datasourceLocal.insertAccountToRoom(account)
    }

    override suspend fun getAccountsFromDatabase(): Resource<List<Accounts>> {
        return datasourceLocal.getAccountsFromDatabase()
    }

    override suspend fun getGuestSessionId(): Resource<String> {
        return datasourceLocal.getGuestSessionId()
    }

    override suspend fun getTokenNew(): Resource<String> {
        return datasourceLocal.getTokenNew()
    }

    override suspend fun createTokenActivated(getToken: Token): Resource<String> {
        return datasourceLocal.createTokenActivated(getToken)
    }

    override suspend fun createSessionId(tokenValidate: String): Resource<String> {
        return datasourceLocal.createSessionId(tokenValidate)
    }

    override suspend fun getGenre(): Resource<List<GenresDB>> {
        return datasourceLocal.getGenre()
    }

    override suspend fun getNowPlaying(): Resource<List<resultsDB>> {
        return datasourceLocal.getNowPlaying()
    }

    override suspend fun getUpcomingMovies(): Resource<List<resultsDB>> {
        return datasourceLocal.getUpcomingMovies()
    }

    override suspend fun getMoviesPopular(): Resource<List<resultsDB>> {
        return datasourceLocal.getMoviesPopular()
    }

    override suspend fun getMoviesTopRated(): Resource<List<resultsDB>> {
        return datasourceLocal.getMoviesTopRated()
    }

    override suspend fun getSearchMulti(search: String, mode: String): Resource<List<resultsDB>> {
        datasourceNetwork.updateSearchMulti(search, mode)
        return datasourceLocal.getSearchMulti(search, mode)
    }

    override suspend fun getAiringTodayTvShow(): Resource<List<resultsDB>> {
        return datasourceLocal.getAiringTodayTvShow()
    }

    override suspend fun getTvShowPopular(): Resource<List<resultsDB>> {
        return datasourceLocal.getTvShowPopular()
    }

    override suspend fun getTvShowTopRated(): Resource<List<resultsDB>> {

        return datasourceLocal.getTvShowTopRated()
    }

    override suspend fun getDetailsOfMovie(id: Int, title: String): Resource<normalDetailsOfMovie> {
        datasourceNetwork.updateDetailsOfMovie(id)
        return datasourceLocal.getDetailsOfMovie(id, title)
    }

    override suspend fun getMovieInformationById(id: Int): resultsDB {
        return datasourceLocal.getMovieInformationById(id)
    }

}