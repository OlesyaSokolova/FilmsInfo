package ru.nsu.fit.sokolova.filmsinfo.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository

class GetFilmsListUseCase (
	private val repository: FilmsRepository
) {
	operator fun invoke(): Flow<Resource<List<FilmInfo>>> {
		return repository.getFilmList()
	}
}