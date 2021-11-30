package ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.search

import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm

data class SearchDto(
    val errorMessage: String,
    val expression: String,
    val results: List<FilmDto>,
    val searchType: String
)

fun SearchDto.toSearchedFilms(): List<SearchedFilm> {
    return results.map { it.toSearchedFilm() }
}