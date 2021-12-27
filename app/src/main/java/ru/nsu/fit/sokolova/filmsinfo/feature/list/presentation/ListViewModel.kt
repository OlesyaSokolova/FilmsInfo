package ru.nsu.fit.sokolova.filmsinfo.feature.list.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.AddFilmUseCase
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.GetFilmListUseCase
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.SearchFilmUseCase
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.UpdateFilmStatusUseCase
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
	private val getFilmListUseCase: GetFilmListUseCase,
	private val searchFilmUseCase: SearchFilmUseCase,
	private val addFilmUseCase: AddFilmUseCase,
	private val setFilmAsWatchedUseCase: UpdateFilmStatusUseCase,
) : ViewModel() {
	private val _filmList: MutableStateFlow<Resource<List<FilmInList>>> =
		MutableStateFlow(Resource.Success(emptyList()))

	val filmList: StateFlow<Resource<List<FilmInList>>> = _filmList

	private val _searchResult: MutableStateFlow<Resource<List<SearchedFilm>>> =
		MutableStateFlow(Resource.Success(emptyList()))

	val searchResult: StateFlow<Resource<List<SearchedFilm>>> = _searchResult

	init {
		getFilmList()
	}


	fun getFilmList() {
		viewModelScope.launch {
			getFilmListUseCase().collect { result ->
				_filmList.value = result
			}
		}
	}

	fun searchByTitle(title: String) {
		_searchResult.value = Resource.Loading
		viewModelScope.launch {
			searchFilmUseCase(title).collect { result ->
				_searchResult.value = result
			}
		}
	}

	fun addFilm(selectedFilm: SearchedFilm) {
		val filmInList = selectedFilm.toFilmInList()
		addFilmUseCase.invoke(filmInList.toFilmInfo())
		getFilmList()
	}

	fun setFilmAsWatched(imdbTitleId: String, isWatched: Boolean) {
		setFilmAsWatchedUseCase(imdbTitleId, isWatched)
	}
}