package ru.nsu.fit.sokolova.filmsinfo.presentation.film_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

class FilmListAdapter(private val films: List<FilmInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.film_thumbnail, parent, false)
        return FilmViewHolder(view)
    //TODO: add onClick to every film (pass it to FilmViewHolder as an argument)
        //onClick is known - open FilmInfoFradment
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val title = holder.itemView.findViewById<TextView>(R.id.tvFilmTitle)
        title.text = films[position].title

        val description = holder.itemView.findViewById<TextView>(R.id.tvFilmDescription)
        description.text = films[position].year + "," + films[position].type

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