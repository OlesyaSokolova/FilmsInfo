package ru.nsu.fit.sokolova.filmsinfo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInList
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

class MainAdapter (
	private val context: Context,
	private val itemClickListener: OnSelectedFilmClickListener
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
		val title = holder.itemView.findViewById<TextView>(R.id.tvFilmTitle)
		title.text = filmList[position].title

		val description = holder.itemView.findViewById<TextView>(R.id.tvFilmDescription)
		//change to film description
		//description.text =  filmList[position].description
		description.text = filmList[position].year + "," + filmList[position].type

		val isWatched = holder.itemView.findViewById<CheckBox>(R.id.cbWatched)
		isWatched.setOnCheckedChangeListener { buttonView, isChecked ->
			filmList[position].isWatched = isChecked
		}
		holder.itemView.setOnClickListener {
			itemClickListener.onSelectedFilmClick(filmList[position])
		}
	}
	override fun getItemCount(): Int = filmList.size

	inner class FilmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}