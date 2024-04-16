package co.meteto.cat.core.generic

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Failure(val message: String? = null) : DataState<Nothing>()
}