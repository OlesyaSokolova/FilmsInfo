package ru.nsu.fit.sokolova.filmsinfo.domain.use_cases

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository

class SearchFilmUseCase (
	private val repository: FilmsRepository
) {
	operator fun invoke(): Flow<Resource<List<FilmInfo>>> {
		return repository.getFilmsList()
	}
}