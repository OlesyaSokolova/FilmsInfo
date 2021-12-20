package ru.nsu.fit.sokolova.filmsinfo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.sokolova.filmsinfo.R


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val mainFragment = MainFragment()
		if(savedInstanceState == null) {
			supportFragmentManager
				.beginTransaction()
				.add(R.id.fragmentHolder, mainFragment, "main fragment")
				.commit()
		}
	}

	fun replaceFragment(fragment: Fragment, tag: String) {
		supportFragmentManager
			.beginTransaction()
			.replace(R.id.fragmentHolder, fragment, tag)
			.commit()
	}
}
