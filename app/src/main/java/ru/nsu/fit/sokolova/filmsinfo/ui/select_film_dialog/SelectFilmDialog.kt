package ru.nsu.fit.sokolova.filmsinfo.ui.select_film_dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R

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
		val searchedFilmsRecyclerView =
			selectDialogView.findViewById<RecyclerView>(R.id.rvSearchedFilms)
		setTitle("SELECT FILM!!!!!!!")
		searchedFilmsRecyclerView?.adapter = adapter
		searchedFilmsRecyclerView.layoutManager = LinearLayoutManager(dialogContext)
		searchedFilmsRecyclerView.scrollToPosition(adapter.itemCount - 1)
		setContentView(selectDialogView)
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