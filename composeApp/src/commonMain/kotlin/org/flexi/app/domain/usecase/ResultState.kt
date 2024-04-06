package org.flexi.app.domain.usecase

sealed class ResultState<out T>{
    object Loading: ResultState<Nothing>()
    data class Success<T>(val response: T): ResultState<T>()
    data class Error(val error: Throwable): ResultState<Nothing>()
}
