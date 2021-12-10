package ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.search

import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm

data class FilmDto(
    val description: String,
    val id: String,//imdbTitleId
    val image: String,
    val resultType: String,
    val title: String
)

fun FilmDto.toSearchedFilm(): SearchedFilm {
    return SearchedFilm(
        description = description,
        imdbTitleId = id,
        title = title
    )
}