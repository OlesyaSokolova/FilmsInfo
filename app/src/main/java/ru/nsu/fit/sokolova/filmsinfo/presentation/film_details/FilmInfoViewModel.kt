package ru.nsu.fit.sokolova.filmsinfo.presentation.film_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.GetFilmInfoUseCase
import javax.inject.Inject

@HiltViewModel
class FilmInfoViewModel @Inject constructor(
	private val getFilmInfoUseCase: GetFilmInfoUseCase,
): ViewModel()
{
	private val _filmInfo: MutableStateFlow<Resource<FilmInfo>> = MutableStateFlow(Resource.Loading)
	val filmInfo: StateFlow<Resource<FilmInfo>> = _filmInfo

	fun getFilmInfo(imdbTitleId: String) {

		viewModelScope.launch {
			getFilmInfoUseCase(imdbTitleId).collect { result ->
				_filmInfo.value = result
			}
		}
	}
}