package ru.nsu.fit.sokolova.filmsinfo.domain.model

import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.title.*

data class FilmInfo(
	val id: String,
	val imdbTitleId: String,
	val title: String,
	val originalTitle: String?,
	val fullTitle: String?,
	val type: String?,
	val year: String?,
	val runtimeStr: String?,
	val image: String?,
	val plot: String?,
	val directors: String?,
	val stars: String?,
	val genres: String?,
	val countries: String?,
	val languages: String?,
	val imDbRating: String?,
	val isWatched: Boolean
) {
	fun hasNoEnoughInfo(): Boolean {
		return (originalTitle == null
				|| fullTitle == null
				|| type == null
				|| year == null
				|| runtimeStr == null
				|| image == null
				|| plot == null
				|| directors == null
				|| stars == null
				|| genres == null
				|| countries == null
				|| languages == null
				|| imDbRating == null)
	}
}
