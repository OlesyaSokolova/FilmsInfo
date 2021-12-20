package ru.nsu.fit.sokolova.filmsinfo.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nsu.fit.sokolova.filmsinfo.data.local.FilmsDatabase
import ru.nsu.fit.sokolova.filmsinfo.data.remote.IMDbApi
import ru.nsu.fit.sokolova.filmsinfo.data.repository.FilmsRepositoryImpl
import ru.nsu.fit.sokolova.filmsinfo.domain.repository.FilmsRepository
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideGetFilmInfoUseCase(repository: FilmsRepository): GetFilmInfoUseCase {
		return GetFilmInfoUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideGetFilmsListUseCase(repository: FilmsRepository): GetFilmListUseCase {
		return GetFilmListUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideSearchFilmUseCase(repository: FilmsRepository): SearchFilmUseCase {
		return SearchFilmUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideAddFilmUseCase(repository: FilmsRepository): AddFilmUseCase {
		return AddFilmUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideUpdateFilmStatusUseCase(repository: FilmsRepository): UpdateFilmStatusUseCase {
		return UpdateFilmStatusUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideFilmsRepository(
		db: FilmsDatabase,
		api: IMDbApi
	): FilmsRepository {
		return FilmsRepositoryImpl(api, db.dao)
	}

	@Provides
	@Singleton
	fun provideFilmsDatabase(app: Application): FilmsDatabase {
		return Room.databaseBuilder(
			app, FilmsDatabase::class.java, "films_db"
		).build()
	//.addTypeConverter(Converters(GsonParser(Gson)))
	}

	@Provides
	@Singleton
	fun provideFilmsInfoApi(): IMDbApi {
		return Retrofit.Builder()
			.baseUrl(IMDbApi.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(IMDbApi::class.java)
	}
}