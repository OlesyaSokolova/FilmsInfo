package ru.nsu.fit.sokolova.filmsinfo.presentation.film_list.select_film

import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R

class SelectListAdapter(
	private val searchedFilms: List<SearchedFilm>,
	private val onSelectedFilmClick: (SearchedFilm) -> View.OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.searched_film, parent, false)
		return SearchResultViewHolder(view)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val title = holder.itemView.findViewById<TextView>(R.id.tvSearchedFilmTitle)
		title.text = searchedFilms[position].title

		val description = holder.itemView.findViewById<CheckBox>(R.id.tvSearchedFilmDesc)
		description.text = searchedFilms[position].description

		//TODO: add onClick to every film - save film to db - do it better, write directly to db, use view model

		holder.itemView.setOnClickListener(onSelectedFilmClick(searchedFilms[position]))
	}

	override fun getItemCount(): Int {
		return searchedFilms.size
	}

	inner class SearchResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}