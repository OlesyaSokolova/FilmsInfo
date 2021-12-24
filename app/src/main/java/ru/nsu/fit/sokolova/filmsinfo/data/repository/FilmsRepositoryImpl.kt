package ru.nsu.fit.sokolova.filmsinfo.data.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.data.local.FilmInfoDao
import ru.nsu.fit.sokolova.filmsinfo.data.remote.IMDbApi
import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.search.toSearchedFilms
import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.title.toFilmInfoEntity
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository
import java.io.IOException

class FilmsRepositoryImpl(
	private val remoteDataSource: IMDbApi,
	private val localDataSource: FilmInfoDao
): FilmsRepository {

	override fun getFilmInfo(imdbTitleId: String): Flow<Resource<FilmInfo>> = flow {
		emit(Resource.Loading)
		/*first we get film info from database;
		if it doesn't exist, we are trying to get remote data*/
		val filmInfo = localDataSource.getFilmInfoByImdbTitleId(imdbTitleId).toFilmInfo()
		if (filmInfo.hasNoEnoughInfo()) {
			try {
				//get remote film info
				val remoteFilmInfo = remoteDataSource.getFilmInfoByImdbTitleId(filmInfo.imdbTitleId)
				val newEntity = remoteFilmInfo.toFilmInfoEntity()
				newEntity.isWatched = filmInfo.isWatched
				localDataSource.deleteByImdbTitleId(filmInfo.imdbTitleId)
				localDataSource.insertFilmInfo(newEntity)
				val updatedFilmInfo =  localDataSource.getFilmInfoByImdbTitleId(imdbTitleId).toFilmInfo()
				emit(Resource.Success(updatedFilmInfo))
			}
			catch (e: Exception) {
				emit(Resource.Failure(e))
			}
		}
		else {
			emit(Resource.Success(filmInfo))
		}
	}

	override fun getFilmList(): Flow<Resource<List<FilmInList>>> = flow {
		val tmp = localDataSource.getAll()
		val filmList = tmp.map { it.toFilmInList() }
		emit(Resource.Success(filmList))
	}

	override fun addFilm(filmInfo: FilmInfo) {
		GlobalScope.launch { localDataSource.insertFilmInfo(filmInfo.toFilmInfoEntity()) }
	}

	override fun updateFilmStatus(imdbTitleId: String, isWatched: Boolean) {
		GlobalScope.launch { localDataSource.updateFilmStatus(imdbTitleId, isWatched) }
	}

	override fun searchFilm(title: String): Flow<Resource<List<SearchedFilm>>> = flow {

		try {
			var searchedFilms: List<SearchedFilm>?  = null
			//get remote film info
			searchedFilms = remoteDataSource.search(title).toSearchedFilms()
			if(searchedFilms != null) {
				emit(Resource.Success(searchedFilms))
			}
			else {
				emit(Resource.Failure(java.lang.Exception("No films with the title!")));
			}
		}
		catch (e: Exception) {
			emit(Resource.Failure(e))
		}

	}
}