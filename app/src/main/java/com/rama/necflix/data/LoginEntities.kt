package com.rama.necflix.data

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rama.necflix.R

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

@Entity(tableName = "draws")
data class DrawableResourceUrl(
    @PrimaryKey
    @ColumnInfo
    val name: String = "",
    @ColumnInfo
    val url: String = ""
)