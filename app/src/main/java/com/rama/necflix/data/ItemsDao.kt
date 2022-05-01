package com.rama.necflix.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrawableName(imgName: DrawableResourceName)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: Accounts)
}