package ru.nsu.fit.sokolova.filmsinfo.domain.use_cases

import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository

class DeleteFilmUseCase(
	private val repository: FilmsRepository
) {
	operator fun invoke(imdbTitleId: String) {
		return repository.deleteFilmFilm(imdbTitleId)
	}
}