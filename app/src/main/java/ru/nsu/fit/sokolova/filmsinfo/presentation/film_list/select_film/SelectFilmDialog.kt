package ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.select_film

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import ru.nsu.fit.sokolova.filmsinfo.R

class SelectFilmDialog(
	context: Context,
	view: View,
	onSearchedFilmClick: View.OnClickListener
) : Dialog(context)
{
//only if there are more than one film with the title
	init{
		setContentView(view)
		setTitle(R.string.select_searched_film)
		val btnSave = view.findViewById<Button>(R.id.clSearchedFilm)
		btnSave.setOnClickListener(onSearchedFilmClick)
	}
}