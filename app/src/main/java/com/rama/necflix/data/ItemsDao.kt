package com.rama.necflix.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrawableName(imgUrl: DrawableResourceUrl)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Accounts)
    @Query("SELECT * FROM draws")
    suspend fun getDrawableSrc(): List<DrawableResourceUrl>
    @Query("SELECT * FROM ACCOUNTS")
    suspend fun getAccountsFromDatabase(): List<Accounts>
}