package com.rama.necflix

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rama.necflix.data.*


@Database(entities = [(Accounts::class),(AccountInvitado::class),(GenresDB::class),(NowPlayingDB::class)], version = 6, exportSchema = false)
abstract class RoomData: RoomDatabase() {

    abstract fun itemsDao(): ItemsDao
}