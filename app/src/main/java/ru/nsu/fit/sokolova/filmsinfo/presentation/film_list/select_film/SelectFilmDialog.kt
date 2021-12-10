package ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.select_film

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm

class SelectFilmDialog(
	private var dialogContext: Context,
	private var adapter: SelectListAdapter
	//view: View,
	//optionsToSelect: List<SearchedFilm>?,
) : Dialog(dialogContext)
{
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val selectDialogView = getLayoutInflater().inflate(R.layout.select_film_dialog, null)
		val searchedFilmsView =
			selectDialogView.findViewById<RecyclerView>(R.id.rvSearchedFilms)
		setTitle("SELECT FILM!!!!!!!")
		searchedFilmsView?.adapter = adapter
		searchedFilmsView.layoutManager = LinearLayoutManager(dialogContext)
		searchedFilmsView.scrollToPosition(adapter.itemCount - 1)
		setContentView(searchedFilmsView)
	}


	//private var userSelection: SearchedFilm

	/*fun getUserSelection(): SearchedFilm {

	}*/

	//even if there is only one film, app suggests user to select it
	/*init{
		if(optionsToSelect == null) {
			//show that there is no film
		}
		else {
			//val listAdapter = SelectListAdapter(optionsToSelect, onSelectedFilmClick)
			//val searchedFilmList = findViewById<RecyclerView>(R.id.rvFilms)
			//searchedFilmList?.adapter = listAdapter
			//searchedFilmList.layoutManager = LinearLayoutManager(getContext())
			//searchedFilmList.scrollToPosition(listAdapter.itemCount - 1)

			setContentView(view)
			setTitle(R.string.select_searched_film)
			//val selectedFilm = view.findViewById<ConstraintLayout>(R.id.clSearchedFilm)
			//selectedFilm.setOnClickListener(onSelectedFilmClick)
		}
	}*/
}