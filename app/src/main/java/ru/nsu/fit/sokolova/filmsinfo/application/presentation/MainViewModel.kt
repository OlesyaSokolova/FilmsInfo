package ru.nsu.fit.sokolova.filmsinfo.application.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.DeleteAllInfosUseCase
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.GetFilmInfoUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val deleteAllInfosUseCase: DeleteAllInfosUseCase,
) : ViewModel() {
	fun clearCache() {
		deleteAllInfosUseCase()
	}
}