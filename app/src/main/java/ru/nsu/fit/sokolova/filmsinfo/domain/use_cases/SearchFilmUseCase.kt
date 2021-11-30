package ru.nsu.fit.sokolova.filmsinfo.domain.use_cases

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository

class SearchFilmUseCase (
	private val repository: FilmsRepository
) {
	operator fun invoke(title: String): Flow<Resource<List<SearchedFilm>>> {
		return repository.searchFilm(title)
	}
}