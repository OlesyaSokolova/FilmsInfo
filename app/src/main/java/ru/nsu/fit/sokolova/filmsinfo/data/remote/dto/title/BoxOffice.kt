package ru.nsu.fit.sokolova.filmsinfo.data.remote.dto.title

data class BoxOffice(
    val budget: String,
    val cumulativeWorldwideGross: String,
    val grossUSA: String,
    val openingWeekendUSA: String
)