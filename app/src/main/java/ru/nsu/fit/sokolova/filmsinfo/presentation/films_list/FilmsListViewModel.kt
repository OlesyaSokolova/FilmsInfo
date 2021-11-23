package ru.nsu.fit.sokolova.filmsinfo.presentation.films_list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.GetFilmsListUseCase
import javax.inject.Inject

@HiltViewModel
class FilmsListViewModel @Inject constructor(
	private val getFilmListUseCase: GetFilmsListUseCase
): ViewModel()
{

}