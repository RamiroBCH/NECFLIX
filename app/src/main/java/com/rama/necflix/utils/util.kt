package com.rama.necflix.utils

import com.rama.necflix.data.*

fun mapConvert(results: List<Result>, type: String): List<resultsDB> {
    return results.map {
        resultsDB(
            it.id,
            type,
            it.backdrop_path,
            it.genre_ids[0],
            it.original_language,
            it.original_title,
            it.overview,
            it.popularity,
            it.poster_path,
            it.title,
            it.vote_average,
            it.vote_count
        )
    }
}
fun mapConvertTvShows(results: List<ResultTvShows>, type: String): List<ResultTvShowsDB> {
    return results.map {
        ResultTvShowsDB(
            it.id,
            type,
            it.backdrop_path,
            it.genre_ids[0],
            it.original_language,
            it.original_name,
            it.overview,
            it.popularity,
            it.poster_path,
            it.name,
            it.vote_average,
            it.vote_count
        )
    }
}
fun asNormalResultDB(results: List<ResultTvShowsDB>): List<resultsDB> {
    return results.map {
        resultsDB(
            it.id,
            it.type,
            it.backdrop_path,
            it.genre_ids,
            it.original_language,
            it.original_name,
            it.overview,
            it.popularity,
            it.poster_path,
            it.name,
            it.vote_average,
            it.vote_count
        )
    }
}
fun mapConvertTvShowsFromResult(results: List<Result>, type: String): List<ResultTvShowsDB> {
    return results.map {
        ResultTvShowsDB(
            it.id,
            type,
            it.backdrop_path,
            it.genre_ids[0],
            it.original_language,
            it.original_title,
            it.overview,
            it.popularity,
            it.poster_path,
            it.title,
            it.vote_average,
            it.vote_count
        )
    }
}
fun mapGenres(generes: List<GenreX>): List<GenresDBMovieSelected>{
    return generes.map {
        GenresDBMovieSelected(
            it.id,
            it.name
        )

    }
}
fun mapImagesPoster(title: String, pster: List<Poster>): List<PosterDBMovieSelected>{
    return pster.map {
        PosterDBMovieSelected(
            null,
            title,
            it.file_path
        )
    }
}
fun mapVideos(title: String, vids: List<ResultDetails>): List<VideosDBMovieSelected>{
    return vids.map {
        VideosDBMovieSelected(
            null,
            title,
            it.key
        )
    }
}
fun convertDetails(details: MoviesDetailsByID): MoviesDetailsDB {
    return MoviesDetailsDB(
        details.id,
        details.backdrop_path,
        details.original_language,
        details.original_title,
        details.overview,
        details.poster_path,
        details.release_date,
        details.title,
        details.vote_average
    )
}
fun convertComplete(detailsOfRes: MoviesDetailsDB, genero: List<GenresDBMovieSelected>, posterDBMovieSelectedUr: List<PosterDBMovieSelected>, videosDBMovieSelectedUr: List<VideosDBMovieSelected>): normalDetailsOfMovie {
    return normalDetailsOfMovie(
        detailsOfRes.id,
        detailsOfRes.backdrop_path,
        detailsOfRes.original_language,
        detailsOfRes.original_title,
        detailsOfRes.overview,
        detailsOfRes.poster_path,
        detailsOfRes.release_date,
        detailsOfRes.title,
        genero,
        posterDBMovieSelectedUr,
        videosDBMovieSelectedUr,
        detailsOfRes.vote_average
    )
}