package ru.nsu.fit.sokolova.filmsinfo.domain.model

import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.title.*

data class FilmInfo(
	val id: String,
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
)
