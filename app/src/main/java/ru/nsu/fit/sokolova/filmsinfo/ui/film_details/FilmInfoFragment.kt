package ru.nsu.fit.sokolova.filmsinfo.ui.film_details

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.domain.model.FilmInfo
import ru.nsu.fit.sokolova.filmsinfo.presentation.film_details.FilmInfoViewModel
import ru.nsu.fit.sokolova.filmsinfo.ui.MainActivity
import ru.nsu.fit.sokolova.filmsinfo.ui.MainFragment
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.ImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.lang.Exception
import java.net.URL


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
	private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
		override fun doInBackground(vararg urls: String): Bitmap? {
			val imageURL = urls[0]
			var image: Bitmap? = null
			try {
				val `in` = java.net.URL(imageURL).openStream()
				image = BitmapFactory.decodeStream(`in`)
			}
			catch (e: Exception) {
				e.printStackTrace()
			}
			return image
		}
		override fun onPostExecute(result: Bitmap?) {
			imageView.setImageBitmap(result)
		}
	}

	fun loadImageFromWeb(url: String?, imdbTitle: String): Drawable? {
		var result: Drawable? = null
		GlobalScope.launch {
			try {
				val inputStream: InputStream = URL(url).getContent() as InputStream
				result = Drawable.createFromStream(inputStream, imdbTitle)
			}
			catch (e: Exception) {
				e.printStackTrace()
				null
			}
		}
		return result
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_film_info, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val backButton = view.findViewById<Button>(R.id.btnBack)
		backButton.setOnClickListener {
			(activity as MainActivity).returnToPreviousFragment()
		}

		val imdbTitleId = arguments?.getString(IMDB_TITLE_ID_KEY);
		if (imdbTitleId != null) {
			var filmInfo: FilmInfo
			//var filmInfo = viewModel.getFilmInfoData(imdbTitleId)
			viewModel.getFilmInfo(imdbTitleId).observe(this, Observer {
				//progressBar.showIf { result is Resource.Loading
				it?.let {
					filmInfo = it

					//val img = view.findViewById<ImageView>(R.id.ivPoster)
					DownloadImageFromInternet(view.findViewById<ImageView>(R.id.ivPoster)).execute(filmInfo.image)
					/*img.setImageDrawable(
						loadImageFromWeb(
							filmInfo.image,
							filmInfo.imdbTitleId.toString()
						)
					);*/

					var textLabel: String
					var textToSet: String

					val title = view.findViewById<TextView>(R.id.tvTitle)
					textLabel = "Title"
					textToSet =
						if (filmInfo.title == null) UNKNOWN_CONTENT else filmInfo.title.toString()
					title.setText((textLabel + DELIMITER + textToSet))

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
					plot.setText(filmInfo.plot)
				}
			})
		}
	}
}