package com.rama.necflix.data

data class DrawableResourceUrl(
    val name: String = "",
    val url: String = ""
)

data class requestToken(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)
data class sessionId(
    val session_id: String,
    val success: Boolean
)
data class guestSessionId(
    val expires_at: String,
    val guest_session_id: String,
    val success: Boolean
)
data class Token(
    val password: String,
    val request_token: String,
    val username: String
)

data class genre(
    val genres: List<GenreX>
)

data class GenreX(
    val id: Int,
    val name: String
)

data class moviesPopular(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

data class nowPlaying(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

data class Dates(
    val maximum: String,
    val minimum: String
)

data class upcomingMovies(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

data class topRated(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

data class searchMulti(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

