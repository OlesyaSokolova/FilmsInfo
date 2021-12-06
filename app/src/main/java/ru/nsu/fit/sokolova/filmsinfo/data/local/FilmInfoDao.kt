package ru.nsu.fit.sokolova.filmsinfo.data.local

import androidx.room.*
import ru.nsu.fit.sokolova.filmsinfo.data.local.entity.FilmInfoEntity

@Dao
interface FilmInfoDao {
//TODO: check that info about isWathced is not replaced
	/*@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertFilmInfos(infos: List<FilmInfoEntity>)*/
//todo: dont forget to add "suspend"
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertFilmInfo(info: FilmInfoEntity)

	@Update
	suspend fun updateFilmInfo(info: FilmInfoEntity)

	//deleting everything
	@Query("DELETE FROM filminfoentity WHERE title IN(:titles) ")
	suspend fun delete(titles: List<String>)

	//Fields: id, imdbTitleId, title and isWatched are not deleted
	@Query("UPDATE	filminfoentity SET " +
				   "originalTitle = null," +
				   "fullTitle = null," +
				   "type = null," +
				   "year = null," +
				   "runtimeStr = null," +
				   "image = null," +
				   "plot = null," +
				   "directors = null," +
				   "stars = null," +
				   "genres = null," +
				   "countries = null," +
				   "languages = null," +
				   "imDbRating = null")
	suspend fun deleteFilmInfos()

	@Query("SELECT * FROM filminfoentity")
	suspend fun getAll(): List<FilmInfoEntity>

	//user clicks at film title in the list: id is known parameter
	@Query("SELECT * FROM filminfoentity WHERE id = :id LIMIT 1")
	suspend fun getFilmInfo(id: Int): FilmInfoEntity
}