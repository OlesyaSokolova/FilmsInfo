package ru.nsu.fit.sokolova.filmsinfo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nsu.fit.sokolova.filmsinfo.data.local.entity.FilmInfoEntity
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

@Dao
interface FilmInfoDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertFilmInfos(infos: List<FilmInfoEntity>)

	@Query("SELECT * FROM filminfoentity")
	fun getAll(): List<FilmInfoEntity>

	//user clicks at film title in the list: title & id are known parameters
	@Query("SELECT * FROM filminfoentity WHERE id = :id LIMIT 1")
	fun getFilmInfo(id: Int): FilmInfoEntity
}