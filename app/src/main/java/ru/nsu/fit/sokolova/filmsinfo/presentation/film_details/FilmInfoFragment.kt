package ru.nsu.fit.sokolova.filmsinfo.presentation.film_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

class FilmInfoFragment : Fragment() {
	 companion object {
		 private const val IMDB_TITLE_ID_KEY = "imdb_title_id"
	 	fun newInstance(imdbTitleId: String): FilmInfoFragment {
			var args = Bundle()
			args.putSerializable(IMDB_TITLE_ID_KEY, imdbTitleId)
	 		return FilmInfoFragment()
	 	}
	 }

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_film_info, container, false)
	}
}