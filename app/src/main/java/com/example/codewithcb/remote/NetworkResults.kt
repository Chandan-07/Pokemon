package com.example.codewithcb.remote

sealed class NetworkResults<T> {
    data class Loading<T>(val isLoading: Boolean) : NetworkResults<T>()
    data class Success<T>(val data: T) : NetworkResults<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResults<T>()
}
