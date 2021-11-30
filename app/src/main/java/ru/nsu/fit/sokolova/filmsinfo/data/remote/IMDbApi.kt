package ru.nsu.fit.sokolova.filmsinfo.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.search.SearchDto
import ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.title.TitleDto

interface IMDbApi {
	 @GET("/" + LANG +"/API/Search/"+ API_KEY + "/{title}")
	 suspend fun search(
		 @Path("title") title: String
	 ): SearchDto

	@GET("/" + LANG + "/API/Title/"+ API_KEY + "/{id}")
	suspend fun getInfoByTitleId(
		@Path("id") id: String
	): TitleDto

	 companion object {
		 const val LANG = "en"
		 const val API_KEY = "k_6t2ujrf5"
		 const val BASE_URL = "https://imdb-api.com/"
	 }
}