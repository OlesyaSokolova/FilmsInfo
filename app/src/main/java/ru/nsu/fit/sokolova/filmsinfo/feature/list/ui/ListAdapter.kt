package ru.nsu.fit.sokolova.filmsinfo.feature.list.ui

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList

class ListAdapter(
	private val itemClickListener: OnSelectedFilmClickListener,
	private val onCheckedChangeListener: (imdbTitleId: String, isWatched: Boolean) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var filmList = mutableListOf<FilmInList>()

	interface OnSelectedFilmClickListener {
		fun onSelectedFilmClick(filmInList: FilmInList)
	}

	fun setFilmList(filmList: List<FilmInList>) {
		this.filmList = filmList as MutableList<FilmInList>
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view =
			LayoutInflater.from(parent.context).inflate(R.layout.film_thumbnail, parent, false)
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
	fun getFilmList(): List<FilmInList> {
		return filmList
	}

	fun deleteFilm(position: Int) {
		filmList.removeAt(position)
		notifyItemRemoved(position)
	}

	inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}