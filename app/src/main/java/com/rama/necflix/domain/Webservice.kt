package com.rama.necflix.domain

import com.rama.necflix.data.*
import retrofit2.http.*

interface Webservice {
    @GET("authentication/token/new")
    suspend fun getTokenNew(
        @Query("api_key") api_key: String
    ): requestToken
    @POST("authentication/token/validate_with_login")
    suspend fun createTokenActivated(
        @Query("api_key") api_key: String,
        @Body getToken: Token
    ): requestToken
    @POST("authentication/session/new")
    suspend fun createSessionId(
        @Query("api_key") api_key: String,
        @Body request_token: String
    ): sessionId

    @GET("authentication/guest_session/new")
    suspend fun getGuestSessionId(
        @Query("api_key") api_key: String
    ): guestSessionId

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") api_key: String
    ): genre

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") api_key: String,
        @Query("language") language : String
    ): nowPlaying

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): upcomingMovies

    @GET("movie/popular")
    suspend fun getMoviesPopular(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): moviesPopular

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): topRated

    @GET("search/movie")
    suspend fun getSearchMulti(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): searchMulti

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): airingTodayTvShow

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): popularTVShows

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): topRatedTvShows

    @GET("movie/{movie_id}")
    suspend fun getMoviesDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("append_to_response") append_to_response: List<String>
    ): MoviesDetailsByID
}