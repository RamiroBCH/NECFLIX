package com.rama.necflix.data

import android.graphics.drawable.Drawable
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
    val imgSrc:Drawable
)

@Entity(tableName = "draws")
data class DrawableResourceName(
    @PrimaryKey
    @ColumnInfo
    val name: String = ""
)