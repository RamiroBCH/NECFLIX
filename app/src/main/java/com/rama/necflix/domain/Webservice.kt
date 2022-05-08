package com.rama.necflix.domain

import com.rama.necflix.data.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Webservice {
    @GET("/authentication/token/new")
    suspend fun getTokenNew(
        @Query("api_key") api_key: String
    ): requestToken
    @POST("/authentication/token/validate_with_login")
    suspend fun createTokenActivated(
        @Query("api_key") api_key: String,
        @Body getToken: Token
    ): requestToken
    @POST("/authentication/session/new")
    suspend fun createSessionId(
        @Query("api_key") api_key: String,
        @Body request_token: String
    ): sessionId


    @GET("/authentication/guest_session/new")
    suspend fun getGuestSessionId(
        @Query("api_key") api_key: String
    ): guestSessionId

    @GET("/genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") api_key: String
    ): genre

}