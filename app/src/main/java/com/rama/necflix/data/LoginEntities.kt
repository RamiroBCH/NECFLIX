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