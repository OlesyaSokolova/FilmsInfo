package ru.nsu.fit.sokolova.filmsinfo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

@Entity
data class FilmInfoEntity(
	@PrimaryKey(autoGenerate = true) val id: Int? = null,
	val imdbTitleId: String,
	val title: String,
	val originalTitle: String,
	val fullTitle: String,
	val type: String,
	val year: String,
	val runtimeStr: String,
	val image: String,
	val plot: String,
	val directors: String,
	val stars: String,
	val genres: String,
	val countries: String,
	val languages: String,
	val imDbRating: String,

) {
	fun toTitleInfo(): FilmInfo {
		return FilmInfo(
			id = imdbTitleId,
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
		)
	}
}