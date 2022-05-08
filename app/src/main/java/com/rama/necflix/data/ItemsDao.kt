package com.rama.necflix.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: Accounts)

    @Query("SELECT * FROM ACCOUNTS")
    suspend fun getAccountsFromDatabase(): List<Accounts>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountInvitado(token: AccountInvitado)

    @Query("SELECT * FROM INVITADO[0]")
    suspend fun getAccountInvitado(): AccountInvitado

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: GenresDB)

    @Query("SELECT * FROM GENRES")
    suspend fun getGenre(): List<GenresDB>
}