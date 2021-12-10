package ru.nsu.fit.sokolova.filmsinfo.presentation.film_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.AddFilmUseCase
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.GetFilmsListUseCase
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.SearchFilmUseCase
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
	private val getFilmListUseCase: GetFilmsListUseCase,
	private val searchFilmUseCase: SearchFilmUseCase,
	private val addFilmUseCase: AddFilmUseCase
): ViewModel()
{
	private var filmList: MutableLiveData<List<FilmInfo>> = MutableLiveData()
	private var searchResult: MutableLiveData<List<SearchedFilm>> = MutableLiveData()

	//private val _state = mu

	init {
		getFilmListUseCase().onEach { result ->
			when(result) {
				is Resource.Success -> {
					filmList.value = result.data ?: emptyList()
				}
				is Resource.Failure -> {
					//show toast with failure
					filmList.value = emptyList()
				}
				is Resource.Loading -> {
					//show loading bar
					//filmList.value = result.data
				}
			}
		}.launchIn(viewModelScope)
	}

	fun getFilmList() = filmList
	/*fun getFilmsList(): Flow<Resource<List<FilmInfo>>> {
		return getFilmListUseCase.invoke()
	}*/

	fun searchByTitle(title: String)  {
		//var searchResult = emptyList<SearchedFilm>()
			searchFilmUseCase(title)
			.onEach { result ->
				when(result) {
					is Resource.Success -> {
						searchResult.value = result.data
					}
					is Resource.Failure -> {
						//show toast with failure
						searchResult.value = emptyList()
					}
					is Resource.Loading -> {
						//show loading bar
						//filmList.value = result.data
					}
				}
			}.launchIn(viewModelScope)
		//return searchResult
	}
	fun getSearchResult(title: String): MutableLiveData<List<SearchedFilm>> {
		searchByTitle(title);
		return searchResult
	}

		fun addFilm(selectedFilm: SearchedFilm) {
			val filmInfo = selectedFilm.toFilmInfo()
			//filmList.value?.toMutableList()?.add(filmInfo)
			//listAdapter.notifyItemInserted(listAdapter.itemCount)
			addFilmUseCase.invoke(filmInfo)
		}
	}