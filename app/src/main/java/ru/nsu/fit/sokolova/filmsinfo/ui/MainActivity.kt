package ru.nsu.fit.sokolova.filmsinfo.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.ui.film_details.FilmInfoFragment
import ru.nsu.fit.sokolova.filmsinfo.ui.input_film_dialog.FilmInutDialog
import ru.nsu.fit.sokolova.filmsinfo.presentation.MainViewModel
import ru.nsu.fit.sokolova.filmsinfo.ui.select_film_dialog.SelectFilmDialog
import ru.nsu.fit.sokolova.filmsinfo.ui.select_film_dialog.SelectListAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private val viewModel: MainViewModel by viewModels()
	private var films = arrayListOf<FilmInList>()
	private lateinit var mainAdapter: MainAdapter
	private lateinit var selectListAdapter: SelectListAdapter
	private lateinit var addFilmButton: FloatingActionButton
	private lateinit var inputDialog: FilmInutDialog
	private lateinit var selectFilmDialog: SelectFilmDialog

	//private lateinit var addFilmInput:

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initData()

		val inputDialogView = getLayoutInflater().inflate(R.layout.add_film_input, null)
		inputDialog = FilmInutDialog(context = this, view = inputDialogView, onSearchButtonClick = {
			val filmTitle = inputDialog.getUserInput();
			inputDialog.dismiss()
			var searchedFilms: ArrayList<SearchedFilm> = ArrayList()
			viewModel.getSearchResult(filmTitle).observe(this, Observer {
				//progressBar.showIf { result is Resource.Loading
				it?.let {
					searchedFilms = ArrayList(it)
					if (!searchedFilms.isEmpty()) {
						selectListAdapter =
							SelectListAdapter({ selectedFilm: SearchedFilm ->
												  run {
													  viewModel.addFilm(selectedFilm)
													  selectFilmDialog.dismiss()
													  mainAdapter.setFilmList(
														  viewModel.getFilmList().value
															  ?: emptyList()
													  )
													  mainAdapter.notifyItemInserted(mainAdapter.itemCount)
												  }
							})
						selectListAdapter.setFilmList(searchedFilms)
						selectFilmDialog = SelectFilmDialog(dialogContext = this@MainActivity, selectListAdapter)
						selectFilmDialog.show()
					}
				}
			})
		})

		addFilmButton = findViewById(R.id.btnAddFilm)
		addFilmButton.setOnClickListener {
			inputDialog.show()
		}
	}

	private fun initData() {

		mainAdapter = MainAdapter(itemClickListener = object: MainAdapter.OnSelectedFilmClickListener  {
			override fun onSelectedFilmClick(filmInList: FilmInList) {
				val fragment = FilmInfoFragment.newInstance(imdbTitleId = filmInList.imdbTitleId)
				supportFragmentManager
					.beginTransaction()
					.replace(R.id.fragmentHolder, fragment, "film detailed info")
			}
		})
		viewModel.getFilmList().observe(this, Observer {
			//progressBar.showIf { result is Resource.Loading
			it?.let {
				val filmsList = findViewById<RecyclerView>(R.id.rvFilms)
				films = ArrayList(it)
				filmsList?.adapter = mainAdapter
				mainAdapter.setFilmList(it)
				filmsList.layoutManager = LinearLayoutManager(this)
				filmsList.scrollToPosition(mainAdapter.itemCount - 1)
			}
		})
	}
}
