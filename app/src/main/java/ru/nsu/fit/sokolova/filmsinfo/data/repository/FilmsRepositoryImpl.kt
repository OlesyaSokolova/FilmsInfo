package ru.nsu.fit.sokolova.filmsinfo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.data.local.FilmInfoDao
import ru.nsu.fit.sokolova.filmsinfo.data.remote.IMDbApi
import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.search.toSearchedFilms
import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.title.toFilmInfoEntity
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository
import java.io.IOException

class FilmsRepositoryImpl(
	private val api: IMDbApi,
	private val dao: FilmInfoDao
): FilmsRepository {

	override fun getFilmInfo(id: Int): Flow<Resource<FilmInfo>> = flow {
		emit(Resource.Loading())

		/*first we get film info from database;
		if it doesn't exist, we are trying to get remote data*/
		val filmInfo = dao.getFilmInfo(id).toFilmInfo()
		if (!filmInfo.hasNoEnoughInfo()) {
			emit(Resource.Loading(data = filmInfo))
		}

		else {
			try {
				//get remote film info
				val remoteFilmInfo = api.getInfoByTitleId(filmInfo.imdbTitleId)
				dao.updateFilmInfo(remoteFilmInfo.toFilmInfoEntity())
			}
			catch (e: HttpException) {
				emit(
					Resource.Error(
						message = "Oops, something went wrong!",
						data = filmInfo
					)
				)
			}
			catch (e: IOException) {
				emit(
					Resource.Error(
						message = "Couldn't reach server, please check your Internet connection.",
						data = filmInfo
					)
				)
			}

			val newFilmInfo = dao.getFilmInfo(id).toFilmInfo()
			emit(Resource.Success(newFilmInfo))
		}
	}

	override fun getFilmsList(): Flow<Resource<List<FilmInfo>>> = flow {
		emit(Resource.Loading())
		val filmsList = dao.getAll().map { it.toFilmInfo() }
		emit(Resource.Success(filmsList))
	}

	override fun searchFilm(title: String): Flow<Resource<List<SearchedFilm>>> = flow {
		emit(Resource.Loading())

		//first we search for the films with the title from server
		try {
			//get remote film info
			val searchedFilms = api.search(title).toSearchedFilms()
			emit(Resource.Success(searchedFilms))

		}
		catch (e: HttpException) {
			emit(
				Resource.Error(
					message = "Oops, something went wrong!",
					data = emptyList<SearchedFilm>()
				))
		}
		catch (e: IOException) {
			emit(
				Resource.Error(
					message = "Couldn't reach server, please check your Internet connection.",
					data = emptyList<SearchedFilm>()
				))
		}
	}
}