package ru.nsu.fit.sokolova.filmsinfo.domain.use_cases

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository

class GetFilmInfoUseCase (
	private val repository: FilmsRepository
) {
	operator fun invoke(imdbTitleId: String): Flow<Resource<FilmInfo>> {
		return repository.getFilmInfo(imdbTitleId)
	}
}