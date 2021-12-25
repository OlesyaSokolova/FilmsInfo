package ru.nsu.fit.sokolova.filmsinfo.feature.info.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.feature.info.presentation.FilmInfoViewModel
import ru.nsu.fit.sokolova.filmsinfo.application.ui.MainActivity
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.nsu.fit.sokolova.filmsinfo.common.Resource
import java.lang.Exception


@AndroidEntryPoint
class FilmInfoFragment : Fragment() {
	private val viewModel: FilmInfoViewModel by viewModels()

	companion object {
		private const val IMDB_TITLE_ID_KEY = "imdb_title_id"
		private const val UNKNOWN_CONTENT: String = "unknown"
		private const val DELIMITER: String = ": "

		fun newInstance(imdbTitleId: String): FilmInfoFragment {
			val args = Bundle()
			args.putString(IMDB_TITLE_ID_KEY, imdbTitleId)
			val instance = FilmInfoFragment()
			instance.arguments = args
			return instance
		}
	}

	@SuppressLint("StaticFieldLeak")
	@Suppress("DEPRECATION")
	private inner class DownloadImageFromInternet(var imageView: ImageView) :
			AsyncTask<String, Void, Bitmap?>() {
		var currentException: Exception = Exception("no image provided")
		val progressBar = view?.findViewById<ProgressBar>(R.id.pbLoadingList)
		override fun doInBackground(vararg urls: String): Bitmap? {
			progressBar?.visibility = View.VISIBLE
			var image: Bitmap? = null
			try {
				val imageURL = urls[0]
				val `in` = java.net.URL(imageURL).openStream()
				image = BitmapFactory.decodeStream(`in`)
			}
			catch (e: Exception) {
				currentException = e
			}
			return image
		}

		override fun onPostExecute(result: Bitmap?) {

			if (result == null) {
				progressBar?.visibility = View.INVISIBLE
				Toast.makeText(
					getActivity(),
					"Error while loading image:\n" + currentException.message + "\nCheck your internet connection.",
					Toast.LENGTH_LONG
				).show()
			}
			else {
				progressBar?.visibility = View.INVISIBLE
				imageView.setImageBitmap(result)
			}
		}
	}


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.film_info_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val backButton = view.findViewById<FloatingActionButton>(R.id.btnBack)
		backButton.setOnClickListener {
			(activity as MainActivity).returnToPreviousFragment()
		}

		val imdbTitleId = arguments?.getString(IMDB_TITLE_ID_KEY);
		if (imdbTitleId != null) {
			var filmInfo: FilmInfo
			viewModel.getFilmInfo(imdbTitleId)
			lifecycleScope.launch {
				viewModel.filmInfo.collect { result ->
					val progressBar = view.findViewById<ProgressBar>(R.id.pbLoadingList)
					progressBar.visibility = View.VISIBLE
					when (result) {
						is Resource.Loading -> {
							progressBar.visibility = View.VISIBLE
						}
						is Resource.Success -> {
							filmInfo = result.data

							var textLabel: String
							var textToSet: String

							val title = view.findViewById<TextView>(R.id.tvTitle)
							textToSet = filmInfo.title.toString()
							title.setText(textToSet)

							val genre = view.findViewById<TextView>(R.id.tvGenre)
							textLabel = "Genre"
							textToSet =
								if (filmInfo.genres == null) UNKNOWN_CONTENT else filmInfo.genres.toString()
							genre.setText((textLabel + DELIMITER + textToSet))

							val type = view.findViewById<TextView>(R.id.tvType)
							textLabel = "Type"
							textToSet =
								if (filmInfo.type == null) UNKNOWN_CONTENT else filmInfo.type.toString()
							type.setText((textLabel + DELIMITER + textToSet))

							val year = view.findViewById<TextView>(R.id.tvYear)
							textLabel = "Year"
							textToSet =
								if (filmInfo.year == null) UNKNOWN_CONTENT else filmInfo.year.toString()
							year.setText((textLabel + DELIMITER + textToSet))

							val countries =
								view.findViewById<TextView>(ru.nsu.fit.sokolova.filmsinfo.R.id.tvCountries)
							textLabel = "Countries"
							textToSet =
								if (filmInfo.countries == null) UNKNOWN_CONTENT else filmInfo.countries.toString()
							countries.setText((textLabel + DELIMITER + textToSet))

							val languages = view.findViewById<TextView>(R.id.tvLanguage)
							textLabel = "Languages"
							textToSet =
								if (filmInfo.languages == null) UNKNOWN_CONTENT else filmInfo.languages.toString()
							languages.setText((textLabel + DELIMITER + textToSet))

							val rating = view.findViewById<TextView>(R.id.tvRating)
							textLabel = "IMdB rating"
							textToSet =
								if (filmInfo.imDbRating == null) UNKNOWN_CONTENT else filmInfo.imDbRating.toString()
							rating.setText((textLabel + DELIMITER + textToSet))

							val plot = view.findViewById<TextView>(R.id.tvPlot)
							if (filmInfo.plot?.isNotBlank() == true && filmInfo.plot?.isNotBlank() == true) {
								plot.setText(filmInfo.plot)
							}
							else {
								plot.setText(UNKNOWN_CONTENT)
							}

							if (filmInfo.image != null) {
								DownloadImageFromInternet(view.findViewById(R.id.ivPoster)).execute(
									filmInfo.image
								)
							}
							else {
								progressBar.visibility = View.INVISIBLE
								Toast.makeText(
									getActivity(),
									"Error while loading image:\n" + "no image provided." + "\nCheck your internet connection.",
									Toast.LENGTH_LONG
								).show()
							}
						}
						is Resource.Failure -> {
							progressBar.visibility = View.INVISIBLE
							Toast.makeText(
								getActivity(),
								result.exception.message + "\nCheck your internet connection.",
								Toast.LENGTH_LONG
							).show()
						}
					}
				}
			}
		}
	}
}