package com.rama.necflix

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rama.necflix.data.*


@Database(entities = [(Accounts::class),(AccountInvitado::class),(GenresDB::class)], version = 5, exportSchema = false)
abstract class RoomData: RoomDatabase() {

    abstract fun itemsDao(): ItemsDao
}