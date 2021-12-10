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
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_details.FilmInfoFragment
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.FilmInutDialog
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.FilmListViewModel
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.select_film.SelectFilmDialog
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.select_film.SelectListAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private val viewModel: FilmListViewModel by viewModels()
	private var films = ArrayList<FilmInfo>()
	private lateinit var mainAdapter: MainAdapter
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
			var searchedFilms = ArrayList<SearchedFilm>()
			viewModel.getSearchResult(filmTitle).observe(this, Observer {
				//progressBar.showIf { result is Resource.Loading
				it?.let {
					searchedFilms = ArrayList(it)
				}
			})
			//val searchedFilms = viewModel.searchByTitle(filmTitle)
			/*val selectDialogView = getLayoutInflater().inflate(R.layout.select_film_dialog, null)
			if (!searchedFilms.isEmpty()) {
				val searchedFilmsAdapter =
					SelectListAdapter(searchedFilms, { selectedFilm: SearchedFilm ->
						View.OnClickListener {
							//get selected film - here it is
							//val selectedFilm = selectFilmDialog.getUserSelection()
							viewModel.addFilm(selectedFilm)
							mainAdapter.notifyItemInserted(mainAdapter.itemCount)
							selectFilmDialog.dismiss()
							//add selected film
							//films.add(selectedFilm.toFilmInfo())
							mainAdapter.notifyItemInserted(mainAdapter.itemCount)
						}
					})
				val searchedFilmsView =
					selectDialogView.findViewById<RecyclerView>(R.id.rvSearchedFilms)
				searchedFilmsView?.adapter = searchedFilmsAdapter
				searchedFilmsView.layoutManager = LinearLayoutManager(this)
				searchedFilmsView.scrollToPosition(0)

				selectFilmDialog.show()
			}*/
		})


		addFilmButton = findViewById(R.id.btnAddFilm)
		addFilmButton.setOnClickListener {
			inputDialog.show()
		}
	}

	private fun initData() {
		viewModel.getFilmList().observe(this, Observer {
			//progressBar.showIf { result is Resource.Loading
			it?.let {
				films = ArrayList(it)
			}
		})

		mainAdapter = MainAdapter(this, itemClickListener = object: MainAdapter.OnSelectedFilmClickListener  {
			override fun onSelectedFilmClick(filmInList: FilmInList) {
				val fragment = FilmInfoFragment.newInstance(imdbTitleId = filmInList.imdbTitleId)
				supportFragmentManager
					.beginTransaction()
					.replace(R.id.fragmentHolder, fragment, "film detailed info")
			}


		})
		val filmsList = findViewById<RecyclerView>(R.id.rvFilms)
		filmsList?.adapter = mainAdapter
		filmsList.layoutManager = LinearLayoutManager(this)
		filmsList.scrollToPosition(mainAdapter.itemCount - 1)
		//val films2 = viewModel.getFilmsList()

		/* films.add(Film("film1", false))
		 films.add(Film("film2", false))
		 films.add(Film("film3", true))
		 films.add(Film("film4", false))
		 films.add(Film("film5", false))*/
		//films.add(FilmInfo(id = 6, title = "hello",
		//imdbTitleId = "ttt56663", isWatched = false))
	}
}
