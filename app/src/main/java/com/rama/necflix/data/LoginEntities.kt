package com.rama.necflix.data

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ACCOUNTS")
data class Accounts(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: String,
    @ColumnInfo
    val username:String,
    @ColumnInfo
    val password:String,
    @ColumnInfo
    val sessionId:String,
    @ColumnInfo
    val imgSrc:Drawable
)
@Entity(tableName = "IMGS")
data class Drawables(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo
    val imgSrc: Drawable
)