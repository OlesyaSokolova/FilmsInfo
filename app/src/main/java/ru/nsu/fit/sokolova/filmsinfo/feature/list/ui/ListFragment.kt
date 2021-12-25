package ru.nsu.fit.sokolova.filmsinfo.feature.list.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.feature.list.presentation.ListViewModel
import ru.nsu.fit.sokolova.filmsinfo.feature.info.ui.FilmInfoFragment
import ru.nsu.fit.sokolova.filmsinfo.feature.dialogs.input.FilmInutDialog
import ru.nsu.fit.sokolova.filmsinfo.feature.dialogs.select.SelectFilmDialog
import ru.nsu.fit.sokolova.filmsinfo.feature.dialogs.select.SelectListAdapter
import kotlinx.coroutines.flow.collect
import ru.nsu.fit.sokolova.filmsinfo.application.ui.MainActivity

@AndroidEntryPoint
class ListFragment : Fragment () {
	private lateinit var currentContext: Context
	private val viewModel: ListViewModel by viewModels()
	private var films = arrayListOf<FilmInList>()
	private lateinit var listAdapter: ListAdapter
	private lateinit var addFilmButton: FloatingActionButton
	private lateinit var inputDialog: FilmInutDialog
	private lateinit var selectFilmDialog: SelectFilmDialog
	private lateinit var currentView: View

	override fun onAttach(context: Context) {
		super.onAttach(context)
		currentContext = context
	}
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.main_list_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		currentView = view
		initData()

		val inputDialogView = getLayoutInflater().inflate(R.layout.add_film_input, null)
		inputDialog = FilmInutDialog(context = currentContext, view = inputDialogView, onSearchButtonClick = {
			val filmTitle = inputDialog.getUserInput();
			inputDialog.dismiss()

			viewModel.searchByTitle(filmTitle)
			lifecycleScope.launch {
				viewModel.searchResult.collect { result ->
					val progressBar = currentView.findViewById<ProgressBar>(R.id.pbLoadingList)
					progressBar?.visibility = View.VISIBLE
					when (result) {
						is Resource.Loading -> {
							progressBar?.visibility = View.VISIBLE
						}
						is Resource.Success -> {
							var searchedFilms: ArrayList<SearchedFilm> = ArrayList()
							searchedFilms = ArrayList(result.data)
							if (!searchedFilms.isEmpty()) {
								val selectListAdapter =
									SelectListAdapter({ selectedFilm: SearchedFilm ->
															  viewModel.addFilm(selectedFilm)
														      selectFilmDialog.cancel()
															  updateFilmList()
													  })
								selectListAdapter.setFilmList(searchedFilms)
								selectFilmDialog = SelectFilmDialog(
									dialogContext = currentContext, selectListAdapter
								)
								progressBar?.visibility = View.INVISIBLE
								selectFilmDialog.show()
							}
						}

						is Resource.Failure -> {
							progressBar?.visibility = View.INVISIBLE
							Toast.makeText(getActivity(), result.exception.message + ".\nCheck your internet connection.", Toast.LENGTH_LONG).show()
						}
					}
				}
			}
		})

		addFilmButton = view.findViewById(R.id.btnAddFilm)
		addFilmButton.setOnClickListener {
			inputDialog.show()
		}
	}

	private fun initData() {

		listAdapter = ListAdapter(itemClickListener = object: ListAdapter.OnSelectedFilmClickListener  {
			override fun onSelectedFilmClick(filmInList: FilmInList) {
				val fragment = FilmInfoFragment.newInstance(imdbTitleId = filmInList.imdbTitleId)
				(activity as MainActivity).replaceFragment(fragment, "film detailed info")
			}
		}, { imdbTitleId: String, isWatched: Boolean ->
				  viewModel.setFilmAsWatched(imdbTitleId, isWatched)
	    })

		updateFilmList()
	}

	private fun updateFilmList() {
		lifecycleScope.launch {
			viewModel.getFilmList()
			viewModel.filmList.collect { result ->
				val progressBar = currentView.findViewById<ProgressBar>(R.id.pbLoadingList)
				progressBar?.visibility = View.VISIBLE
				when (result) {
					is Resource.Loading -> {
						progressBar?.visibility = View.VISIBLE
					}
					is Resource.Success -> {
						progressBar?.visibility = View.INVISIBLE
						val filmsList = view?.findViewById<RecyclerView>(R.id.rvFilms)
						films = ArrayList(result.data)
						filmsList?.adapter = listAdapter
						listAdapter.setFilmList(films)
						filmsList?.layoutManager = LinearLayoutManager(context)
						filmsList?.scrollToPosition(listAdapter.itemCount - 1)
					}
					is Resource.Failure -> {
						progressBar?.visibility = View.INVISIBLE
						Toast.makeText(getActivity() ,result.exception.message, Toast.LENGTH_LONG).show()
					}
				}
			}
		}
		listAdapter.notifyItemInserted(
			listAdapter.itemCount)
	}
}