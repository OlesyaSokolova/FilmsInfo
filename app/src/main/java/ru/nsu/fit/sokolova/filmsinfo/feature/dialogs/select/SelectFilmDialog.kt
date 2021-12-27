package ru.nsu.fit.sokolova.filmsinfo.feature.dialogs.select

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.feature.list.presentation.ListViewModel

class SelectFilmDialog(
	private val searchedFilms: List<SearchedFilm>,
	private var dialogContext: Context,
	private val onFilmClick: (searchedFilm: SearchedFilm) -> Unit
) : Dialog(dialogContext) {

	init {
		this.setAdapter(SelectListAdapter(itemClickListener = object :
				SelectListAdapter.OnSelectedFilmClickListener {
			override fun onSelectedFilmClick(searchedFilm: SearchedFilm) {
				dismiss()
				onFilmClick(searchedFilm)
			}
		}))
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		super.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	fun setAdapter(adapter: SelectListAdapter) {
		val selectDialogView = getLayoutInflater().inflate(R.layout.select_film_dialog, null)
		val searchedFilmsRecyclerView =
			selectDialogView.findViewById<RecyclerView>(R.id.rvSearchedFilms)
		super.setCanceledOnTouchOutside(true)
		setContentView(selectDialogView)

		adapter.setFilmList(searchedFilms)
		searchedFilmsRecyclerView?.adapter = adapter
		searchedFilmsRecyclerView.layoutManager = LinearLayoutManager(dialogContext)
		searchedFilmsRecyclerView.scrollToPosition(adapter.itemCount - 1)
	}
}