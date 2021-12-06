package ru.nsu.fit.sokolova.filmsinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.FilmInutDialog
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.FilmListAdapter
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.FilmListViewModel
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.select_film.SelectFilmDialog

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: FilmListViewModel by viewModels()
    private val films = ArrayList<FilmInfo>()
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
        val selectDialogView = getLayoutInflater().inflate(R.layout.select_film_dialog, null)
        selectFilmDialog = SelectFilmDialog(
            context = this,
            view = selectDialogView,
            onSearchedFilmClick = addFilm,
        )

        val inputDialogView = getLayoutInflater().inflate(R.layout.add_film_input, null)
        inputDialog = FilmInutDialog(
            context = this,
            view = inputDialogView,
            onSearchButtonClick = searchFilm,
        )

        addFilmButton = findViewById(R.id.btnAddFilm)
        addFilmButton.setOnClickListener {
            inputDialog.show()
        }
    }

    val addFilm = View.OnClickListener {
        //get selected film
        selectFilmDialog.dismiss()
        //add selected film
        //films.add(FilmInfo(filTitle, false))
        listAdapter.notifyItemInserted(listAdapter.itemCount)
    }
    val searchFilm = View.OnClickListener {
        selectFilmDialog.show()
        /*val filTitle = inputDialog.getUserInput()
        inputDialog.dismiss()
        val test = viewModel.onSearch(filTitle)

        //films.add(Film(filTitle, false))
        listAdapter.notifyItemInserted(listAdapter.itemCount)*/
    }


    private fun initData()
    {
        viewModel.getFilmList().observe(this, Observer {
            it?.let {
                listAdapter = FilmListAdapter(it)
            }
        })

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