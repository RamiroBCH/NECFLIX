package com.rama.necflix.domain

import com.rama.necflix.data.*
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(private val datasource: Datasource): Repo {
    override suspend fun insertAccountToRoom(account: Accounts) {
        datasource.insertAccountToRoom(account)
    }

    override suspend fun getAccountsFromDatabase(): Resource<List<Accounts>> {
        return datasource.getAccountsFromDatabase()
    }

    override suspend fun getGuestSessionId(): Resource<String> {
        return datasource.getGuestSessionId()
    }

    override suspend fun getTokenNew(): Resource<String> {
        return datasource.getTokenNew()
    }

    override suspend fun createTokenActivated(getToken: Token): Resource<String> {
        return datasource.createTokenActivated(getToken)
    }

    override suspend fun createSessionId(tokenValidate: String): Resource<String> {
        return datasource.createSessionId(tokenValidate)
    }

    override suspend fun getGenre(): Resource<List<GenresDB>> {
        return datasource.getGenre()
    }

    override suspend fun getNowPlaying(): Resource<List<resultsDB>> {
        return datasource.getNowPlaying()
    }

    override suspend fun getUpcomingMovies(): Resource<List<resultsDB>> {
        return datasource.getUpcomingMovies()
    }

    override suspend fun getMoviesPopular(): Resource<List<resultsDB>> {
        return datasource.getMoviesPopular()
    }

    override suspend fun getMoviesTopRated(): Resource<List<resultsDB>> {
        return datasource.getMoviesTopRated()
    }

    override suspend fun getSearchMulti(search: String, mode: String): Resource<List<resultsDB>> {
        return datasource.getSearchMulti(search, mode)
    }

    override suspend fun getAiringTodayTvShow(): Resource<List<resultsDB>> {
        return datasource.getAiringTodayTvShow()
    }

    override suspend fun getTvShowPopular(): Resource<List<resultsDB>> {
        return datasource.getTvShowPopular()
    }

    override suspend fun getTvShowTopRated(): Resource<List<resultsDB>> {
        return datasource.getTvShowTopRated()
    }

    override suspend fun getDetailsOfMovie(id: Int): Resource<normalDetailsOfMovie> {
        return datasource.getDetailsOfMovie(id)
    }

}