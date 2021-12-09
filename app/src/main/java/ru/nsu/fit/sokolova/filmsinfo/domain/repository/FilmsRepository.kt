package ru.nsu.fit.sokolova.filmsinfo.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm

interface FilmsRepository {
	fun getFilmInfo(imdbTitleId: String): Flow<Resource<FilmInfo>>
	fun getFilmList(): Flow<Resource<List<FilmInfo>>>
	fun addFilm(filmInfo: FilmInfo)
	fun searchFilm(title: String): Flow<Resource<List<SearchedFilm>>>
}