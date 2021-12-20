package ru.nsu.fit.sokolova.filmsinfo.domain.model

data class FilmInList(
	val imdbTitleId: String,
	val description: String,
	val title: String,
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