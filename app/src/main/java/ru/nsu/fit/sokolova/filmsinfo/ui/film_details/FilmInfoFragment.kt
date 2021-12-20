package ru.nsu.fit.sokolova.filmsinfo.ui.film_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo

import ru.nsu.fit.sokolova.filmsinfo.presentation.film_details.FilmInfoViewModel

@AndroidEntryPoint
class FilmInfoFragment : Fragment() {

	private val UNKNOWN_CONTENT: String = "unknown"//TODO: create const
	private val DELIMITER: String = ": "//TODO: create const
	private val viewModel: FilmInfoViewModel by viewModels()

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
		return inflater.inflate(R.layout.fragment_film_info, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val imdbTitleId = arguments?.getString(IMDB_TITLE_ID_KEY);
		if(imdbTitleId != null) {
			var filmInfo: FilmInfo
			viewModel.getFilmInfo(imdbTitleId).observe(this, Observer {
				//progressBar.showIf { result is Resource.Loading
				it?.let {
					filmInfo = it

					var textLabel: String
					var textToSet: String

					val title = view.findViewById<TextView>(R.id.tvTitle)
					textLabel = "Title"
					textToSet = if(filmInfo.title == null) UNKNOWN_CONTENT  else filmInfo.title.toString()
					title.setText((textLabel + DELIMITER + textToSet))

					val type = view.findViewById<TextView>(R.id.tvType)
					textLabel = "Type"
					textToSet = if(filmInfo.type == null) UNKNOWN_CONTENT else filmInfo.type.toString()
					type.setText((textLabel + DELIMITER + textToSet))

					val year = view.findViewById<TextView>(R.id.tvYear)
					textLabel = "Year"
					textToSet = if(filmInfo.year == null) UNKNOWN_CONTENT else filmInfo.year.toString()
					year.setText((textLabel + DELIMITER + textToSet))

					val countries = view.findViewById<TextView>(R.id.tvCountries)
					textLabel = "Countries"
					textToSet = if(filmInfo.countries == null) UNKNOWN_CONTENT else filmInfo.countries.toString()
					countries.setText((textLabel + DELIMITER + textToSet))

					val languages = view.findViewById<TextView>(R.id.tvLanguage)
					textLabel = "Languages"
					textToSet = if(filmInfo.languages == null) UNKNOWN_CONTENT else filmInfo.languages.toString()
					languages.setText((textLabel + DELIMITER + textToSet))

					val rating = view.findViewById<TextView>(R.id.tvRating)
					textLabel = "IMdB rating"
					textToSet = if(filmInfo.imDbRating == null) UNKNOWN_CONTENT else filmInfo.imDbRating.toString()
					rating.setText((textLabel + DELIMITER + textToSet))

					val plot = view.findViewById<TextView>(R.id.tvPlot)
					plot.setText(filmInfo.plot)
				}
			})
		}
	}
}