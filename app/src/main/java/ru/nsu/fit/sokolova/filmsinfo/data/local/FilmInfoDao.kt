package ru.nsu.fit.sokolova.filmsinfo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nsu.fit.sokolova.filmsinfo.data.local.entity.FilmInfoEntity

@Dao
interface FilmInfoDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertFilmInfos(infos: List<FilmInfoEntity>)

	//clearing chache
	@Query("DELETE FROM filminfoentity WHERE title IN(:titles) ")
	suspend fun deleteFilmInfos(titles: List<String>)


	@Query("SELECT * FROM filminfoentity")
	suspend fun getAll(): List<FilmInfoEntity>

	//user clicks at film title in the list: id is known parameter
	@Query("SELECT * FROM filminfoentity WHERE id = :id LIMIT 1")
	suspend fun getFilmInfo(id: Int): FilmInfoEntity
}