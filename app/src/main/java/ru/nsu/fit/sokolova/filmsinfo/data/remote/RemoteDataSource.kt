package ru.nsu.fit.sokolova.filmsinfo.data.remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.title.TitleDto
import javax.inject.Inject


/*@ExperimentalCoroutinesApi
class RemoteDataSource @Inject constructor(
	private val api: IMDbApi
) {
	suspend fun getFilmInfoByImdbTitleId(imdbTitleId: String): Flow<Resource<TitleDto>> = flow {
		emit(Resource.Success(
			api.getFilmInfoByImdbTitleId(imdbTitleId)))
	}
}*/
