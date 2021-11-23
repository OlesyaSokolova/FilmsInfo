package ru.nsu.fit.sokolova.filmsinfo.data.remote.dto

import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

data class FilmInfoDto(
	val title: String
) {
	fun toFilmInfo(): FilmInfo {
		return FilmInfo()
	}
}
