package com.rama.necflix.data

import com.rama.necflix.domain.Datasource
import com.rama.necflix.domain.Webservice
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class DatasourceImpl @Inject constructor(private val itemsDao: ItemsDao, private val webservice: Webservice) :
    Datasource {
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

    override suspend fun getSearchMulti(search: String, mode: String): Resource<List<resultsDB>> {
        return if(mode == "movie"){
            val moviesSearchMultiList = webservice.getSearchMulti(apikey, language, search).results
            val lisConvert = mapConvert(moviesSearchMultiList, search)
            insertDB(lisConvert)
            val moviesSearchMultiListDB = itemsDao.getMoviesFromDB(search)
            Resource.Success(moviesSearchMultiListDB)
        }else{
            val tvShowsSearchMultiList = webservice.getSearchMulti(apikey, language, search).results
            val listConvert = mapConvertTvShowsFromResult(tvShowsSearchMultiList, search)
            insertTVShowsDB(listConvert)
            val tvShowsSearchMultiListDB = itemsDao.getTvShowsFromDB(search)
            Resource.Success(asNormalResultDB(tvShowsSearchMultiListDB))
        }
    }

    override suspend fun getAiringTodayTvShow(): Resource<List<resultsDB>> {
        val type = "AiringToday"
        val tvShowsAiringToday = webservice.getAiringTodayTvShows(apikey, language).results
        val listConvert = mapConvertTvShows(tvShowsAiringToday, type)
        insertTVShowsDB(listConvert)
        val tvShowsAiringTodayDB = itemsDao.getTvShowsFromDB(type)
        return Resource.Success(asNormalResultDB(tvShowsAiringTodayDB))
    }

    override suspend fun getTvShowPopular(): Resource<List<resultsDB>> {
        val type = "Tv Popular"
        val tvShowsPopular = webservice.getPopularTvShows(apikey, language).results
        val listConvert = mapConvertTvShows(tvShowsPopular, type)
        insertTVShowsDB(listConvert)
        val tvShowsPopularDB = itemsDao.getTvShowsFromDB(type)
        return Resource.Success(asNormalResultDB(tvShowsPopularDB))
    }

    override suspend fun getTvShowTopRated(): Resource<List<resultsDB>> {
        val type = "Tv Top Rated"
        val tvShowsTopRated = webservice.getTopRatedTvShows(apikey, language).results
        val listConvert = mapConvertTvShows(tvShowsTopRated, type)
        insertTVShowsDB(listConvert)
        val tvShowsTopRatedDB = itemsDao.getTvShowsFromDB(type)
        return Resource.Success(asNormalResultDB(tvShowsTopRatedDB))
    }

    override suspend fun getDetailsOfMovie(id: Int): Resource<normalDetailsOfMovie> {
        val details = webservice.getMoviesDetails(id, apikey, language, append)
        val generos = mapGenres(details.genres)
        val posterUrl: List<PosterDBMovieSelected>
        val videosUrl: List<VideosDBMovieSelected>
        if(details.images != null){
            posterUrl = mapImagesPoster(details.title, details.images.posters)
            insertDetailsPoster(posterUrl)
        }
        if(details.videos != null){
            videosUrl = mapVideos(details.title, details.videos.results)
            insertDetailsMovie(videosUrl)
        }
        val detailsOfRest = convertDetails(details)
        insertDetailsGenres(generos)
        insertMoviesDetailsDB(detailsOfRest)
        val genero = itemsDao.getDetailsGenres(id)
        val posterUr = itemsDao.getDetailsPoster(details.title)
        val videosUr = itemsDao.getDetailsVideos(details.title)
        val detailsOfRes = itemsDao.getMoviesDetailsDB(id)
        val detailsComplete = convertComplete(detailsOfRes, genero, posterUr, videosUr)
        return Resource.Success(detailsComplete)
    }
    //******************************************************************************************
    //funciones para insertar en room
    private suspend fun insertDB(convert: List<resultsDB>) {
        for(i in convert.indices) {
            itemsDao.insertUpcomingMovie(
                resultsDB(
                    convert[i].id,
                    convert[i].type,
                    convert[i].backdrop_path,
                    convert[i].genre_ids,
                    convert[i].original_language,
                    convert[i].original_title,
                    convert[i].overview,
                    convert[i].popularity,
                    convert[i].poster_path,
                    convert[i].title,
                    convert[i].vote_average,
                    convert[i].vote_count
                )
            )
        }
    }
    private suspend fun insertTVShowsDB(convert: List<ResultTvShowsDB>){
        for(i in convert.indices){
            itemsDao.insertTvShows(
                ResultTvShowsDB(
                    convert[i].id,
                    convert[i].type,
                    convert[i].backdrop_path,
                    convert[i].genre_ids,
                    convert[i].original_language,
                    convert[i].original_name,
                    convert[i].overview,
                    convert[i].popularity,
                    convert[i].poster_path,
                    convert[i].name,
                    convert[i].vote_average,
                    convert[i].vote_count
                )
            )
        }
    }
    private suspend fun insertDetailsGenres(list: List<GenresDBMovieSelected>){
        for (i in list.indices){
            itemsDao.insertDetailsGenres(
                GenresDBMovieSelected(
                    list[i].id,
                    list[i].name
                )
            )
        }
    }

    private suspend fun insertDetailsPoster(list: List<PosterDBMovieSelected>){
        for (i in list.indices){
            itemsDao.insertDetailsPoster(
                PosterDBMovieSelected(
                    list[i].id,
                    list[i].name,
                    list[i].url
                )
            )
        }
    }
    private suspend fun insertDetailsMovie(list: List<VideosDBMovieSelected>){
        for(i in list.indices){
            itemsDao.insertDetailsVideos(
                VideosDBMovieSelected(
                    list[i].id,
                    list[i].name,
                    list[i].url
                )
            )
        }
    }
    private suspend fun insertMoviesDetailsDB(details: MoviesDetailsDB){
        itemsDao.insertMoviesDetailsDB(details)
    }
}

private fun mapConvert(results: List<Result>, type: String): List<resultsDB> {
    return results.map {
        resultsDB(
            it.id,
            type,
            it.backdrop_path,
            it.genre_ids[0],
            it.original_language,
            it.original_title,
            it.overview,
            it.popularity,
            it.poster_path,
            it.title,
            it.vote_average,
            it.vote_count
        )
    }
}
private fun mapConvertTvShows(results: List<ResultTvShows>, type: String): List<ResultTvShowsDB> {
    return results.map {
        ResultTvShowsDB(
            it.id,
            type,
            it.backdrop_path,
            it.genre_ids[0],
            it.original_language,
            it.original_name,
            it.overview,
            it.popularity,
            it.poster_path,
            it.name,
            it.vote_average,
            it.vote_count
        )
    }
}
private fun asNormalResultDB(results: List<ResultTvShowsDB>): List<resultsDB> {
    return results.map {
        resultsDB(
            it.id,
            it.type,
            it.backdrop_path,
            it.genre_ids,
            it.original_language,
            it.original_name,
            it.overview,
            it.popularity,
            it.poster_path,
            it.name,
            it.vote_average,
            it.vote_count
        )
    }
}
private fun mapConvertTvShowsFromResult(results: List<Result>, type: String): List<ResultTvShowsDB> {
    return results.map {
        ResultTvShowsDB(
            it.id,
            type,
            it.backdrop_path,
            it.genre_ids[0],
            it.original_language,
            it.original_title,
            it.overview,
            it.popularity,
            it.poster_path,
            it.title,
            it.vote_average,
            it.vote_count
        )
    }
}
private fun mapGenres(generes: List<GenreX>): List<GenresDBMovieSelected>{
    return generes.map {
        GenresDBMovieSelected(
            it.id,
            it.name
        )

    }
}
private fun mapImagesPoster(title: String, pster: List<Poster>): List<PosterDBMovieSelected>{
    return pster.map {
        PosterDBMovieSelected(
            null,
            title,
            it.file_path
        )
    }
}
private fun mapVideos(title: String, vids: List<ResultDetails>): List<VideosDBMovieSelected>{
    return vids.map {
        VideosDBMovieSelected(
            null,
            title,
            it.key
        )
    }
}
private fun convertDetails(details: MoviesDetailsByID): MoviesDetailsDB{
    return MoviesDetailsDB(
        details.id,
        details.backdrop_path,
        details.original_language,
        details.original_title,
        details.overview,
        details.poster_path,
        details.release_date,
        details.title,
        details.vote_average
    )
}
private fun convertComplete(detailsOfRes: MoviesDetailsDB, genero: List<GenresDBMovieSelected>, posterDBMovieSelectedUr: List<PosterDBMovieSelected>, videosDBMovieSelectedUr: List<VideosDBMovieSelected>): normalDetailsOfMovie {
    return normalDetailsOfMovie(
        detailsOfRes.id,
        detailsOfRes.backdrop_path,
        detailsOfRes.original_language,
        detailsOfRes.original_title,
        detailsOfRes.overview,
        detailsOfRes.poster_path,
        detailsOfRes.release_date,
        detailsOfRes.title,
        genero,
        posterDBMovieSelectedUr,
        videosDBMovieSelectedUr,
        detailsOfRes.vote_average
    )
}

