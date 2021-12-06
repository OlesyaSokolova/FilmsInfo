package ru.nsu.fit.sokolova.filmsinfo.presentation.film_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.GetFilmsListUseCase
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.SearchFilmUseCase
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
	private val getFilmListUseCase: GetFilmsListUseCase,
	private val searchFilmUseCase: SearchFilmUseCase
): ViewModel()
{
	private var filmList: MutableLiveData<List<FilmInfo>> = MutableLiveData()

	init {
		 getFilmListUseCase().onEach { result ->
			when(result) {
				is Resource.Success -> {
					filmList.value = result.data
				}
				is Resource.Error -> {
					val test = 2;
				}
				is Resource.Loading -> {
					filmList.value = result.data
				}
			}
		}.launchIn(viewModelScope)
	}

	fun getFilmList() = filmList
	/*fun getFilmsList(): Flow<Resource<List<FilmInfo>>> {
		return getFilmListUseCase.invoke()
	}*/

	fun onSearch(title: String) {
		searchFilmUseCase(title).onEach { result ->
			when(result) {
				is Resource.Success -> {
					val test = result.data
				}
				is Resource.Error -> {
					val test = 2;
				}
				is Resource.Loading -> {
					val test = result.data
				}
			}
		}.launchIn(viewModelScope)
	}

}