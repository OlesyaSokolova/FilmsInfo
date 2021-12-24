package ru.nsu.fit.sokolova.filmsinfo.data.local

import androidx.room.*
import ru.nsu.fit.sokolova.filmsinfo.data.local.entity.FilmInfoEntity

@Dao
interface FilmInfoDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertFilmInfo(info: FilmInfoEntity)

	@Query("SELECT * FROM filminfoentity")
	suspend fun getAll(): List<FilmInfoEntity>

	//user clicks at film title in the list: id is known parameter
	@Query("SELECT * FROM filminfoentity WHERE imdbTitleId = :imdbTitleId LIMIT 1")
	suspend fun getFilmInfoByImdbTitleId(imdbTitleId: String): FilmInfoEntity

	@Query("UPDATE filminfoentity SET isWatched = :isWatched WHERE imdbTitleId = :imdbTitleId")
	suspend fun updateFilmStatus(imdbTitleId: String, isWatched: Boolean)



	@Query("UPDATE filminfoentity SET originalTitle = :originalTitle, fullTitle = :fullTitle, type = :type, year =:year, runtimeStr =:runtimeStr, image =:image, plot= :plot, directors = :directors, stars = :stars, genres = :genres, countries = :countries, languages = :languages, imDbRating = :imDbRating WHERE imdbTitleId = :imdbTitleId")
	suspend fun updateFilmInfo(originalTitle: String,
							   fullTitle: String,
							   type: String,
							   year: String,
							   runtimeStr: String,
							   image: String,
							   plot: String,
							   directors: String,
							   stars: String,
							   genres: String,
							   countries: String,
							   languages: String,
							   imDbRating: String,
							   imdbTitleId: String)

	@Query("UPDATE filminfoentity SET originalTitle = null, fullTitle = null, type = null, year = null, runtimeStr = null, image = null, plot = null, directors = null, stars = null, genres = null, countries = null, languages = null, imDbRating = null")
	suspend fun deleteAllInfos()
}