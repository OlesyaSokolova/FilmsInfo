package ru.nsu.fit.sokolova.filmsinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.Film
import ru.nsu.fit.sokolova.filmsinfo.presentation.films_list.FilmInutDialog
import ru.nsu.fit.sokolova.filmsinfo.presentation.films_list.FilmsListAdapter

class MainActivity : AppCompatActivity() {

    private val films = ArrayList<Film>()
    private lateinit var listAdapter: FilmsListAdapter
    private lateinit var addFilmButton: FloatingActionButton
    private lateinit var inputDialog: FilmInutDialog
    //private lateinit var addFilmInput:

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //progress bar during constant time -> redirect user to list
        //or open list immediately
        initData()

        listAdapter = FilmsListAdapter(films)
        //TODO: think how and where will be better to initialize this variable
        val filmsList = findViewById<RecyclerView>(R.id.rvFilms)
        filmsList?.adapter = listAdapter
        filmsList.layoutManager = LinearLayoutManager(this)
        filmsList.scrollToPosition(listAdapter.itemCount - 1)
        //listAdapter.replaceList(tasks)

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

    val searchFilm = View.OnClickListener {
        val filTitle = inputDialog.getUserInput()
        inputDialog.dismiss()
        films.add(Film(filTitle, false))
        listAdapter.notifyItemInserted(listAdapter.itemCount)
    }


    private fun initData()
    {
        films.add(Film("film1", false))
        films.add(Film("film2", false))
        films.add(Film("film3", true))
        films.add(Film("film4", false))
        films.add(Film("film5", false))
    }
}