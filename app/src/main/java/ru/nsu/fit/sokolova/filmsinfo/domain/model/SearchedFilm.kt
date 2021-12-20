package ru.nsu.fit.sokolova.filmsinfo.domain.model

data class SearchedFilm(
	val description: String,
	val imdbTitleId: String,
	val title: String
) {
	fun toFilmInList(): FilmInList {
		return FilmInList(
			imdbTitleId = imdbTitleId,
			title = title,
			type = null,
			year = null,
			description = description,
			isWatched = false
		)
	}
}


