package com.kiral.charityapp.network

sealed class DataState<out T>{
    object Loading: DataState<Nothing>()
    data class Success<out T>(val data: T): DataState<T>()
    data class HttpsErrorCode(val code: Int, val message: String): DataState<Nothing>()
    data class Error(val error: String): DataState<Nothing>()
}