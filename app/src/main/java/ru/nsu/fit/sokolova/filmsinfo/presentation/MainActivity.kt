package ru.nsu.fit.sokolova.filmsinfo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.nsu.fit.sokolova.filmsinfo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //progress bar during constant time -> redirect user to list
        //or open list immediately
    }
}