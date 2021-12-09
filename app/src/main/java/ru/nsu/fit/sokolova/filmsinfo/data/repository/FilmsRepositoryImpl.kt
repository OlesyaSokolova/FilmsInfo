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
				localDataSource.updateFilmInfo(remoteFilmInfo.toFilmInfoEntity())
			}
			catch (e: Exception) {
				emit(Resource.Failure(e))
			}
		}
		val updatedFilmInfos =  localDataSource.getFilmInfoByImdbTitleId(imdbTitleId).toFilmInfo()
		emit(Resource.Success(updatedFilmInfos))
	}

	override fun getFilmList(): Flow<Resource<List<FilmInfo>>> = flow {
		val filmsList = localDataSource.getAll().map { it.toFilmInfo() }
		emit(Resource.Success(filmsList))
	}

	override fun addFilm(filmInfo: FilmInfo) {
		localDataSource.insertFilmInfo(filmInfo.toFilmInfoEntity())
	}

	override fun searchFilm(title: String): Flow<Resource<List<SearchedFilm>>> = flow {
		var searchedFilms: List<SearchedFilm>? = null;
		//first we search for the films with the title from server
		try {
			//get remote film info
			searchedFilms = remoteDataSource.search(title).toSearchedFilms()
			//Log.d("filmtest", searchedFilms.toString())

		}
		catch (e: Exception) {
			emit(Resource.Failure(e))
		}
		if(searchedFilms != null) {
			emit(Resource.Success(searchedFilms))
		}
		else {
			emit(Resource.Failure(java.lang.Exception("No films with the title!")));
		}
	}
}