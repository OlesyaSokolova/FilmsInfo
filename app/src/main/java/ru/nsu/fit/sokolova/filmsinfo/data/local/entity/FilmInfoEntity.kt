package ru.nsu.fit.sokolova.filmsinfo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

@Entity
class FilmInfoEntity(
	@PrimaryKey(autoGenerate = true) val id: Int? = null,
	val title: String
) {
	fun toFilmInfo(): FilmInfo {
		return FilmInfo(
			title = title,
		)
	}
}