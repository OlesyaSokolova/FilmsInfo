package ru.nsu.fit.sokolova.filmsinfo.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.sokolova.filmsinfo.common.Resource

interface FilmsRepository {
	fun getFilmInfo(id: Int): Flow<Resource<FilmInfo>>
	fun getFilmsList(): Flow<Resource<List<FilmInfo>>>
	fun searchFilm(title: String): Flow<Resource<List<FilmInfo>>>
}