package com.rama.necflix.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ACCOUNTS")
data class Accounts(
    @PrimaryKey
    @ColumnInfo
    val username:String,
    @ColumnInfo
    val password:String,
    @ColumnInfo
    val requestToken:String,
    @ColumnInfo
    val sessionId:String?,
    @ColumnInfo
    val imgSrc: String
)

@Entity(tableName = "INVITADO")
data class AccountInvitado(
    @PrimaryKey
    val id: Int,
    @ColumnInfo
    val token: String
)

@Entity(tableName = "GENRES")
data class GenresDB(
    @PrimaryKey
    @ColumnInfo
    val id: Int,
    @ColumnInfo
    val name:String
)

@Entity(tableName = "RESULTSDB")
data class resultsDB(
    @PrimaryKey
    @ColumnInfo
    val id: Int,
    @ColumnInfo
    val type: String,
    @ColumnInfo
    val adult: Boolean,
    @ColumnInfo
    val backdrop_path: String,
    @ColumnInfo
    val genre_ids: Int,
    @ColumnInfo
    val original_language: String,
    @ColumnInfo
    val original_title: String,
    @ColumnInfo
    val overview: String,
    @ColumnInfo
    val popularity: Double,
    @ColumnInfo
    val poster_path: String,
    @ColumnInfo
    val release_date: String,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val video: Boolean,
    @ColumnInfo
    val vote_average: Double,
    @ColumnInfo
    val vote_count: Int
)