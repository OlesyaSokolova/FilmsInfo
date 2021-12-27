package ru.nsu.fit.sokolova.filmsinfo.feature.dialogs.select

import ru.nsu.fit.sokolova.filmsinfo.domain.model.SearchedFilm
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.feature.list.ui.ListAdapter

class SelectListAdapter(
	private val itemClickListener: OnSelectedFilmClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	private var searchedFilms = emptyList<SearchedFilm>()

	interface OnSelectedFilmClickListener {
		fun onSelectedFilmClick(searchedFilm: SearchedFilm)
	}

	fun setFilmList(searchedFilms: List<SearchedFilm>) {
		this.searchedFilms = searchedFilms
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view =
			LayoutInflater.from(parent.context).inflate(R.layout.searched_film, parent, false)
		return SearchResultViewHolder(view)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val title = holder.itemView.findViewById<TextView>(R.id.tvSearchedFilmTitle)
		title.text = searchedFilms[position].title

		val description = holder.itemView.findViewById<TextView>(R.id.tvSearchedFilmDesc)
		description.text = searchedFilms[position].description

		holder.itemView.setOnClickListener {
			itemClickListener.onSelectedFilmClick(searchedFilms[position])
		}
	}

	override fun getItemCount(): Int {
		return searchedFilms.size
	}


	inner class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}