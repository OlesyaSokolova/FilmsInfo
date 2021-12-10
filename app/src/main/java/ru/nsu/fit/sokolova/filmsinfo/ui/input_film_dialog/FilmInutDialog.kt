package ru.nsu.fit.sokolova.filmsinfo.ui.input_film_dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import ru.nsu.fit.sokolova.filmsinfo.R

class FilmInutDialog(
    context: Context,
    view: View,
    onSearchButtonClick: View.OnClickListener
) : Dialog(context)
{
    private var userInput: EditText

    fun getUserInput(): String {
        return userInput.text.toString()
    }

    init{
        setContentView(view)
        setTitle(R.string.input_film_title)

        userInput = view.findViewById<EditText>(R.id.etFilmTitle)
        userInput.requestFocus()

        val btnSave = view.findViewById<Button>(R.id.btnFilmInputSearch)
        btnSave.setOnClickListener(onSearchButtonClick)

        val btnCancel = view.findViewById<Button>(R.id.btnFilmInputCancel)
        btnCancel.setOnClickListener{
            this.cancel()
        }
    }

    override fun dismiss() {
        userInput.getText().clear()
        super.dismiss()
    }
}