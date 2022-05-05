package com.rama.necflix

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rama.necflix.data.Accounts
import com.rama.necflix.data.DrawableResourceUrl
import com.rama.necflix.data.ItemsDao


@Database(entities = [(Accounts::class), (DrawableResourceUrl::class)], version = 1, exportSchema = false)
abstract class RoomData: RoomDatabase() {

    abstract fun itemsDao(): ItemsDao
}