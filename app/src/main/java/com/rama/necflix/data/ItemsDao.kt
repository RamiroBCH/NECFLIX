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

    @Query("SELECT * FROM INVITADO WHERE id == :id")
    suspend fun getAccountInvitado(id: Int): AccountInvitado?

    @Query("SELECT * FROM ACCOUNTS WHERE isActive == :isActive")
    suspend fun getActiveAcc(isActive: Boolean): Accounts

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: GenresDB)

    @Query("SELECT * FROM GENRES")
    suspend fun getGenre(): List<GenresDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovie(resultsDB :resultsDB)

    @Query("SELECT * FROM RESULTSDB WHERE type == :type")
    suspend fun getMoviesFromDB(type: String): List<resultsDB>

    @Query("SELECT * FROM RESULTSDB WHERE id == :id")
    suspend fun getMovieInformationById(id: Int): resultsDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(resultTvShowsDB: ResultTvShowsDB)
    @Query("SELECT * FROM RESULTTVSHOWSDB WHERE type == :type")
    suspend fun getTvShowsFromDB(type: String): List<ResultTvShowsDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailsGenres(GenresDBMovieSelected: GenresDBMovieSelected)
    @Query("SELECT * FROM GENRESLIST WHERE id == :id")
    suspend fun getDetailsGenres(id: Int): List<GenresDBMovieSelected>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailsVideos(VideosDBMovieSelected: VideosDBMovieSelected)
    @Query("SELECT * FROM VIDEOS WHERE name == :title")
    suspend fun getDetailsVideos(title: String): List<VideosDBMovieSelected>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailsPoster(PosterDBMovieSelected: PosterDBMovieSelected)
    @Query("SELECT * FROM POSTER WHERE name == :title")
    suspend fun getDetailsPoster(title: String): List<PosterDBMovieSelected>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesDetailsDB(moviesDetailsDB: MoviesDetailsDB)
    @Query("SELECT * FROM DETALLES WHERE title == :title")
    suspend fun getMoviesDetailsDB(title: String): MoviesDetailsDB
}