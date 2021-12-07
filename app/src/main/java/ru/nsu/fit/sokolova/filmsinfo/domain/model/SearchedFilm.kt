package ru.nsu.fit.sokolova.filmsinfo.domain.model

data class SearchedFilm(
	val description: String,
	val imdbTitleId: String,
	val title: String
) {
	fun toFilmInfo(): FilmInfo {
		return FilmInfo(
			title = title,
			imdbTitleId = imdbTitleId,
		)
	}
}


