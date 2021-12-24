package ru.nsu.fit.sokolova.filmsinfo.feature.dialogs.select

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R

class SelectFilmDialog(
	private var dialogContext: Context,
	private var adapter: SelectListAdapter
) : Dialog(dialogContext)
{
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val selectDialogView = getLayoutInflater().inflate(R.layout.select_film_dialog, null)
		val searchedFilmsRecyclerView =
			selectDialogView.findViewById<RecyclerView>(R.id.rvSearchedFilms)
		///setTitle("SELECT FILM!!!!!!!")
		searchedFilmsRecyclerView?.adapter = adapter
		searchedFilmsRecyclerView.layoutManager = LinearLayoutManager(dialogContext)
		searchedFilmsRecyclerView.scrollToPosition(adapter.itemCount - 1)
		setContentView(selectDialogView)
	}

	override fun dismiss() {
		adapter.clearSelectionList()
		super.dismiss()
	}
}