package ru.nsu.fit.sokolova.filmsinfo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

@Entity
data class FilmInfoEntity(
	//some fields should not be updated
	@PrimaryKey(autoGenerate = true) val id: Int? = null,//const
	val imdbTitleId: String,////const
	val title: String,////const
	val description: String,//const
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
	fun toFilmInfo(): FilmInfo {
		return FilmInfo(
			id = id,
			imdbTitleId = imdbTitleId,
			description = description,
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

	fun toFilmInList(): FilmInList {
		return FilmInList(
			imdbTitleId = imdbTitleId,
			title = title,
			description = description,
			isWatched = isWatched
		)
	}
}