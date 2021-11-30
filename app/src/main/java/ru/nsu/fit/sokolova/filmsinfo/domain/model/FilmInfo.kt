package ru.nsu.fit.sokolova.filmsinfo.domain.model

import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.title.*

data class FilmInfo(
	val id: Int?,
	val imdbTitleId: String,
	val title: String,
	val originalTitle: String? = null,
	val fullTitle: String? = null,
	val type: String? = null,
	val year: String? = null,
	val runtimeStr: String? = null,
	val image: String? = null,
	val plot: String? = null,
	val directors: String? = null,
	val stars: String? = null,
	val genres: String? = null,
	val countries: String? = null,
	val languages: String? = null,
	val imDbRating: String? = null,
	var isWatched: Boolean
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
