package ru.nsu.fit.sokolova.filmsinfo.presentation.film_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

class FilmInfoFragment : Fragment() {
	 companion object {
		 private const val IMDB_TITLE_ID_KEY = "imdb_title_id"

	 	fun newInstance(imdbTitleId: String): FilmInfoFragment {
			val args = Bundle()
			args.putString(IMDB_TITLE_ID_KEY, imdbTitleId)
			val instance = FilmInfoFragment()
			instance.arguments = args
	 		return instance
	 	}
	 }

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_film_info, container, false)
		val imdbTitleId = arguments?.getString(IMDB_TITLE_ID_KEY);
		///get data from repository
		//var imdbid = view.findViewById<TextView>(R.id.view)
		return view;
	}
}