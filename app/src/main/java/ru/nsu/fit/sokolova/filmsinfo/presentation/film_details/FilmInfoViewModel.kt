package ru.nsu.fit.sokolova.filmsinfo.presentation.film_details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.fit.sokolova.filmsinfo.domain.use_cases.GetFilmInfoUseCase
import javax.inject.Inject

@HiltViewModel
class FilmInfoViewModel @Inject constructor(
	private val getFilmInfoUseCase: GetFilmInfoUseCase,
): ViewModel()
{

}