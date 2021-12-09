package ru.nsu.fit.sokolova.filmsinfo.common

import java.lang.Exception

//sealed class Resource<T>(val data: T? = null, val message: String? = null) {
sealed class Resource<out T> {
	object Loading: Resource<Nothing>()
	data class Success<out T>(val data: T): Resource<T>()
	data class Failure(val exception: Exception): Resource<Nothing>()
	//class Loading<T>(data: T? = null): Resource<T>(data)
	//class Success<T>(data: T?): Resource<T>(data)
	//class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
	//class Error<T>(message: String): Resource<T>(message = message)
}