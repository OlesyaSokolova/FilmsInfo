package ru.nsu.fit.sokolova.filmsinfo.domain.model

import ru.nsu.fit.sokolova.filmsinfo.data.local.entity.FilmInfoEntity
import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.title.*

data class FilmInfo(
	val id: Int? = null,
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
	var isWatched: Boolean = false
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

	fun toFilmInfoEntity(): FilmInfoEntity {
		return FilmInfoEntity(
			imdbTitleId = imdbTitleId,
			title = title,
			originalTitle = originalTitle,
			fullTitle = fullTitle,
			type = type,
			year = year,
			runtimeStr = runtimeStr,
			image = image,
			plot = plot,
			directors = directors,
			stars = stars,
			genres = genres,
			countries = countries,
			languages = languages,
			imDbRating = imDbRating,
			isWatched = isWatched
		)
	}
}
