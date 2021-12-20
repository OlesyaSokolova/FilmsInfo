package ru.nsu.fit.sokolova.filmsinfo.domain.model

import java.io.FileDescriptor

data class FilmInList(
	val type: String?,
	val imdbTitleId: String,
	val description: String,
	val title: String,
	val year: String?,
	var isWatched: Boolean
) {
	fun toFilmInfo(): FilmInfo {
		return FilmInfo(
			title = title,
			imdbTitleId = imdbTitleId,
			description = description,
			isWatched = isWatched
		)
	}
}