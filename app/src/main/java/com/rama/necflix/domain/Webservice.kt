package com.rama.necflix.domain

import com.rama.necflix.data.requestToken
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {
    @GET("/authentication/token/new")
    suspend fun getTokenNew(
        @Query("api_key") api_key: String
    ): requestToken
}