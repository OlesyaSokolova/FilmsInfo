package ru.nsu.fit.sokolova.filmsinfo.application.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.sokolova.filmsinfo.R
import ru.nsu.fit.sokolova.filmsinfo.application.presentation.MainViewModel
import ru.nsu.fit.sokolova.filmsinfo.feature.info.presentation.FilmInfoViewModel
import ru.nsu.fit.sokolova.filmsinfo.feature.list.ui.ListFragment


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private val viewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val mainFragment = ListFragment()
		if (savedInstanceState == null) {
			replaceFragment(mainFragment, "main fragment")
		}
	}

	fun replaceFragment(fragment: Fragment, tag: String) {
		supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, fragment, tag)
			.addToBackStack(null).commit()
	}

	fun returnToPreviousFragment() {
		if (supportFragmentManager.getBackStackEntryCount() > 0) {
			supportFragmentManager.popBackStack();
		}
		else {
			super.onBackPressed()
		}
	}

	override fun onDestroy() {
		//viewModel.clearCache()
		super.onDestroy()
	}
}
