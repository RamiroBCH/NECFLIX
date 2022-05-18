package com.rama.necflix.data

import com.rama.necflix.domain.Webservice
import com.rama.necflix.network.DatasourceNetwork
import com.rama.necflix.utils.*
import javax.inject.Inject

class DatasourceNetworkImpl @Inject constructor(private val itemsDao: ItemsDao, private val webservice: Webservice):
    DatasourceNetwork {
    private val apikey = "f937e50a9ebe2079954b39dfed897360"
    private val language = "es"
    private val append = "videos,images"

    override suspend fun updateNowPlaying() {
        val type = "nowplaying"
        val nowPlayingList = webservice.getNowPlaying(apikey, language).results
        val convert = mapConvert(nowPlayingList,type)
        insertDB(convert)
    }

    override suspend fun updateUpcomingMovies() {
        val type = "Upcoming"
        val upcomingMoviesList = webservice.getUpcomingMovies(apikey, language).results
        val convert = mapConvert(upcomingMoviesList,type)
        insertDB(convert)
    }

    override suspend fun updateMoviesPopular() {
        val type = "Popular"
        val moviesPopularList = webservice.getMoviesPopular(apikey, language).results
        val listConvert = mapConvert(moviesPopularList,type)
        insertDB(listConvert)
    }

    override suspend fun updateMoviesTopRated() {
        val type = "Top Rated"
        val moviesTopRatedList = webservice.getTopRated(apikey, language).results
        val listConvert = mapConvert(moviesTopRatedList, type)
        insertDB(listConvert)
    }

    override suspend fun updateSearchMulti(search: String, mode: String) {
        if(mode == "movie"){
            val moviesSearchMultiList = webservice.getSearchMulti(apikey, language, search).results
            val lisConvert = mapConvert(moviesSearchMultiList, search)
            insertDB(lisConvert)
        }else{
            val tvShowsSearchMultiList = webservice.getSearchMulti(apikey, language, search).results
            val listConvert = mapConvertTvShowsFromResult(tvShowsSearchMultiList, search)
            insertTVShowsDB(listConvert)
        }
    }

    override suspend fun updateAiringTodayTvShow() {
        val type = "AiringToday"
        val tvShowsAiringToday = webservice.getAiringTodayTvShows(apikey, language).results
        val listConvert = mapConvertTvShows(tvShowsAiringToday, type)
        insertTVShowsDB(listConvert)
    }

    override suspend fun updateTvShowPopular() {
        val type = "Tv Popular"
        val tvShowsPopular = webservice.getPopularTvShows(apikey, language).results
        val listConvert = mapConvertTvShows(tvShowsPopular, type)
        insertTVShowsDB(listConvert)
    }

    override suspend fun updateTvShowTopRated() {
        val type = "Tv Top Rated"
        val tvShowsTopRated = webservice.getTopRatedTvShows(apikey, language).results
        val listConvert = mapConvertTvShows(tvShowsTopRated, type)
        insertTVShowsDB(listConvert)
    }

    override suspend fun updateDetailsOfMovie(id: Int) {
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