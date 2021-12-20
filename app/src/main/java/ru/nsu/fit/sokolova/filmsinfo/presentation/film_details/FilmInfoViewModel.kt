package ru.nsu.fit.sokolova.filmsinfo.presentation.film_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
	private var filmInfo: MutableLiveData<FilmInfo> = MutableLiveData()

	private fun loadFilmInfo(imdbTitleId: String) {
		getFilmInfoUseCase(imdbTitleId).onEach { result ->
			when(result) {
				is Resource.Success -> {
					filmInfo.value = result.data
				}
				is Resource.Failure -> {
					//show toast with failure
					filmInfo.value = null
				}
				is Resource.Loading -> {
					//show loading bar
				}
			}
		}.launchIn(viewModelScope)
	}

	fun getFilmInfo(imdbTitleId: String): MutableLiveData<FilmInfo> {
		loadFilmInfo(imdbTitleId)
		return filmInfo
	}

	fun getFilmInfoData(imdbTitleId: String): FilmInfo? {
		loadFilmInfo(imdbTitleId)
		return filmInfo.value
	}
}