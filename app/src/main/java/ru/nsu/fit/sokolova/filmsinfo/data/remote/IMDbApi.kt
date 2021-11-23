package ru.nsu.fit.sokolova.filmsinfo.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.FilmInfoDto
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

interface IMDbApi {
	 @GET("/cbfecbfecbef/{title}")
	 suspend fun getFilmInfos(
		 @Path("word") word: String
	 ): List<FilmInfoDto>

	 companion object {
		 const val BASE_URL = "https://imdb-api.com/"
	 }
}