package ru.nsu.fit.sokolova.filmsinfo.domain.use_cases

import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository

class DeleteAllInfosUseCase(
	private val repository: FilmsRepository
) {
	operator fun invoke() {
		return repository.deleteAllInfos()
	}
}