package ru.nsu.fit.sokolova.filmsinfo.presentation.films_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.Film

class FilmsListAdapter(private val films: List<Film>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.film_thumbnail, parent, false)
        return FilmViewHolder(view)
    //TODO: add onClick to every film (pass it to FilmViewHolder as an argument)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val title = holder.itemView.findViewById<TextView>(R.id.tvFilmTitle)
        title.text = films[position].title

        val isWatched = holder.itemView.findViewById<CheckBox>(R.id.cbWatched)
        isWatched.setOnCheckedChangeListener { buttonView, isChecked ->
            films[position].isWatched = isChecked
        }
    }

    override fun getItemCount(): Int {
        return films.size
    }

    inner class FilmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}