package ru.nsu.fit.sokolova.filmsinfo.feature.list.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList

class ListAdapter (
	private val itemClickListener: OnSelectedFilmClickListener,
	private val onCheckedChangeListener: (imdbTitleId: String, isWatched: Boolean) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	private var filmList = emptyList<FilmInList>()

	interface OnSelectedFilmClickListener {
		fun onSelectedFilmClick(filmInList: FilmInList)
	}

	//@SuppressLint("NotifyDataSetChanged")
	fun setFilmList(filmList: List<FilmInList>) {
		this.filmList = filmList
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.film_thumbnail, parent, false)
		return FilmViewHolder(view)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val currentFilm = filmList[position]

		val title = holder.itemView.findViewById<TextView>(R.id.tvFilmTitle)
		title.setText(currentFilm.title)

		val description = holder.itemView.findViewById<TextView>(R.id.tvFilmDescription)
		description.setText(currentFilm.description)

		val isWatched = holder.itemView.findViewById<CheckBox>(R.id.cbWatched)
		isWatched.setChecked(currentFilm.isWatched)
		isWatched.setOnCheckedChangeListener { _, isChecked ->
			filmList[position].isWatched = isChecked
			onCheckedChangeListener(filmList[position].imdbTitleId, isChecked)
		}
		holder.itemView.setOnClickListener {
			itemClickListener.onSelectedFilmClick(filmList[position])
		}
	}
	override fun getItemCount(): Int = filmList.size

	inner class FilmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}