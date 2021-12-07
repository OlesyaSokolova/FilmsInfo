package ru.nsu.fit.sokolova.filmsinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.FilmInutDialog
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.FilmListAdapter
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.FilmListViewModel
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.select_film.SelectFilmDialog
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.select_film.SelectListAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: FilmListViewModel by viewModels()
    private var films = ArrayList<FilmInfo>()
    private lateinit var listAdapter: FilmListAdapter
    private lateinit var addFilmButton: FloatingActionButton
    private lateinit var inputDialog: FilmInutDialog
    private lateinit var selectFilmDialog: SelectFilmDialog

    //private lateinit var addFilmInput:

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //progress bar during constant time -> redirect user to list
        //or open list immediately
        initData()

        //listAdapter.replaceList(tasks)


        val inputDialogView = getLayoutInflater().inflate(R.layout.add_film_input, null)
        inputDialog = FilmInutDialog(
            context = this,
            view = inputDialogView,
            onSearchButtonClick = {
                val filmTitle = inputDialog.getUserInput();
                inputDialog.dismiss()
                val searchedFilms = viewModel.searchByTitle(filmTitle)
                val selectDialogView =
                    getLayoutInflater().inflate(R.layout.select_film_dialog, null)
                if (searchedFilms != null) {
                    //убрать флоу - иначе открывается пустой диалог. Переделать SelectDialog
                    val searchedFilmsAdapter = SelectListAdapter(searchedFilms, { selectedFilm: SearchedFilm  -> View.OnClickListener {
                        //get selected film
                        //val selectedFilm = selectFilmDialog.getUserSelection()
                        selectFilmDialog.dismiss()
                        //add selected film
                        films.add(selectedFilm.toFilmInfo())
                        listAdapter.notifyItemInserted(listAdapter.itemCount)
                    }})
                    val searchedFilmsView = selectDialogView.findViewById<RecyclerView>(R.id.rvSearchedFilms)
                    //searchedFilms?.adapter = searchedFilmsAdapter
                    //searchedFilms.layoutManager = LinearLayoutManager(this)

                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setView(selectDialogView)
                    alertDialog.setTitle(R.string.select_searched_film)
                    searchedFilmsView.adapter = searchedFilmsAdapter
                    searchedFilmsView.scrollToPosition(searchedFilmsAdapter.itemCount - 1)
                    alertDialog.show()


                    /* selectFilmDialog = SelectFilmDialog(
						 context = this,
						 view = selectDialogView,
						 optionsToSelect = searchedFilms,
						 onSelectedFilmClick = { selectedFilm: SearchedFilm  -> View.OnClickListener {
							 //get selected film
							 //val selectedFilm = selectFilmDialog.getUserSelection()
							 selectFilmDialog.dismiss()
							 //add selected film
							 films.add(selectedFilm.toFilmInfo())
							 listAdapter.notifyItemInserted(listAdapter.itemCount)
						 }
											   },
					 )*/
                    /* val builder = AlertDialog.Builder(this)
					 builder.setTitle(R.string.select_searched_film)
					 builder.setItems(searchedFilms)*/
                    //selectFilmDialog.show()
                }
            }

        )

        addFilmButton = findViewById(R.id.btnAddFilm)
        addFilmButton.setOnClickListener {
            inputDialog.show()
        }
    }
    /*val searchFilm = View.OnClickListener {
        selectFilmDialog.show()
        *//*val filTitle = inputDialog.getUserInput()
        inputDialog.dismiss()
        val test = viewModel.onSearch(filTitle)

        //films.add(Film(filTitle, false))
        listAdapter.notifyItemInserted(listAdapter.itemCount)*//*
    }*/

   /* val addFilm = View.OnClickListener {
        //get selected film
        selectFilmDialog.dismiss()
        //add selected film
        //films.add(FilmInfo(filTitle, false))
        listAdapter.notifyItemInserted(listAdapter.itemCount)
    }*/



    private fun initData()
    {
        viewModel.getFilmList().observe(this, Observer {
            it?.let {
                films = ArrayList(it)
            }
        })
        listAdapter = FilmListAdapter(films)
        val filmsList = findViewById<RecyclerView>(R.id.rvFilms)
        filmsList?.adapter = listAdapter
        filmsList.layoutManager = LinearLayoutManager(this)
        filmsList.scrollToPosition(listAdapter.itemCount - 1)
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