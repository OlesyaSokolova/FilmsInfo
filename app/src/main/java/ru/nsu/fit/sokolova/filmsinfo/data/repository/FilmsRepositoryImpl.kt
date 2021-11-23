package ru.nsu.fit.sokolova.filmsinfo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.data.local.FilmInfoDao
import ru.nsu.fit.sokolova.filmsinfo.data.remote.IMDbApi
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository
import java.io.IOException

class FilmsRepositoryImpl(
	private val api: IMDbApi,
	private val dao: FilmInfoDao
): FilmsRepository {

	override fun getFilmInfo(id: Int): Flow<Resource<FilmInfo>> = flow {
		emit(Resource.Loading())

		/*first we get word info from database;
		if it doesn't exist, we are trying to get remote data*/
		val filmInfo = dao.getFilmInfo(id).toFilmInfo()
		emit(Resource.Loading(data = filmInfo))

	/*	try {
			//update film info
			val remoteFilmInfo = api.getFilmInfo(title)
			dao.deleteFilmInfo(remoteFilmInfo)
			dao.insertFilmInfo(remoteFilmInfo.toFilmInfoEntity())
		} catch (e: HttpException) {
			emit(Resource.Error(
				message = "Oops, something went wrong!",
				data = filmInfo
			))
		} catch (e: IOException) {
			emit(Resource.Error(
				message = "Couldn't reach server, please check your Internet connection.",
				data = filmInfo
			))
		}*/

		val newFilmInfo = dao.getFilmInfo(id).toFilmInfo()
		emit(Resource.Success(newFilmInfo))
	}

	override fun getFilmsList(): Flow<Resource<List<FilmInfo>>> = flow {
		emit(Resource.Loading())
		val filmsList = dao.getAll().map { it.toFilmInfo() }
		emit(Resource.Success(filmsList))
	}

	override fun searchFilm(title: String): Flow<Resource<List<FilmInfo>>> = flow {
		emit(Resource.Loading())
		var filmInfos = null
		try {
			//search for film info
			filmInfos = api.getFilmInfos(title)
		} catch (e: HttpException) {
			emit(Resource.Error(
				message = "Oops, something went wrong!",
				data = filmInfos
			))
		} catch (e: IOException) {
			emit(Resource.Error(
				message = "Couldn't reach server, please check your Internet connection.",
				data = filmInfos
			))
		}
	}

}