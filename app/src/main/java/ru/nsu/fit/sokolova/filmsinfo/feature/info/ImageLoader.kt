package ru.nsu.fit.sokolova.filmsinfo.feature.info

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class ImageLoader {
	suspend fun load(url: String): Bitmap? {
		val imageURL = url
		var image: Bitmap? = null
		val `in` = java.net.URL(imageURL).openStream()
		image = BitmapFactory.decodeStream(`in`)
		return image
	}
}