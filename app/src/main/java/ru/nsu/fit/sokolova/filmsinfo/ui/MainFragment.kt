package ru.nsu.fit.sokolova.filmsinfo.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.presentation.MainViewModel
import ru.nsu.fit.sokolova.filmsinfo.ui.film_details.FilmInfoFragment
import ru.nsu.fit.sokolova.filmsinfo.ui.input_film_dialog.FilmInutDialog
import ru.nsu.fit.sokolova.filmsinfo.ui.select_film_dialog.SelectFilmDialog
import ru.nsu.fit.sokolova.filmsinfo.ui.select_film_dialog.SelectListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainFragment : Fragment () {
	private lateinit var currentContext: Context
	private val viewModel: MainViewModel by viewModels()
	private var films = arrayListOf<FilmInList>()
	private lateinit var mainAdapter: MainAdapter
	//private lateinit var selectListAdapter: SelectListAdapter
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
														  run {
															  viewModel.addFilm(selectedFilm)
															  selectFilmDialog.dismiss()
															  updateFilmList()
															  //selectListAdapter.clearSelectionList()
														  }
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
							//showToast(result.exception.messa
							//showTost ("error")
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

		mainAdapter = MainAdapter(itemClickListener = object: MainAdapter.OnSelectedFilmClickListener  {
			override fun onSelectedFilmClick(filmInList: FilmInList) {
				val fragment = FilmInfoFragment.newInstance(imdbTitleId = filmInList.imdbTitleId)
				(activity as MainActivity).replaceFragment(fragment, "film detailed info")
			}
		},
	    { imdbTitleId: String, isWatched: Boolean ->
			  run {
				  viewModel.setFilmAsWatched(imdbTitleId, isWatched)
			  }
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
						filmsList?.adapter = mainAdapter
						mainAdapter.setFilmList(films)
						filmsList?.layoutManager = LinearLayoutManager(context)
						filmsList?.scrollToPosition(mainAdapter.itemCount - 1)
					}
					is Resource.Failure -> {
						progressBar?.visibility = View.INVISIBLE
						//showToast(result.exception.messa
						//showTost ("error")
					}
				}
			}
		}
		mainAdapter.notifyItemInserted(
			mainAdapter.itemCount)
	}
}