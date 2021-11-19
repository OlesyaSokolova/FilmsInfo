package ru.nsu.fit.sokolova.filmsinfo.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

interface FilmsRepository {
	fun getFilmInfo(title: String): Flow<Resource<List<FilmInfo>>>
	fun getFilmsList(): Flow<Resource<List<FilmInfo>>>

}